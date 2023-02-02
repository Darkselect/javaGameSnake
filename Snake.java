package com.javarush.games.snake;


import java.util.ArrayList;
import java.util.List;
import com.javarush.engine.cell.*;


public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    public int x;
    public int y;
    private static final String HEAD_SIGN = "\uD83D\uDC7E";
    private static final String BODY_SIGN = "\u26AB";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;



    public Snake(int x, int y) {
        GameObject fist = new GameObject(x, y);
        GameObject second = new GameObject(x + 1, y);
        GameObject third = new GameObject(x + 2, y);

        snakeParts.add(fist);
        snakeParts.add(second);
        snakeParts.add(third);
    }

    public void setDirection(Direction direction) {
        if (!(((this.direction == Direction.LEFT || this.direction == Direction.RIGHT) &&
                (snakeParts.get(0).x == snakeParts.get(1).x)) ||
                ((this.direction == Direction.UP || this.direction == Direction.DOWN) &&
                        (snakeParts.get(0).y == snakeParts.get(1).y))))
            this.direction = direction;

    }

    public void draw(Game game) {
        Color color = isAlive ? Color.BLACK : Color.RED;

        for (int i = 0; i < snakeParts.size(); i++) {
            GameObject part = snakeParts.get(i);
            String sign = (i != 0) ? BODY_SIGN : HEAD_SIGN;
            game.setCellValueEx(part.x, part.y, Color.NONE, sign, color, 75);
        }
    }

    public void move(Apple apple) {
        GameObject newHead = createNewHead();
        if (newHead.x >= SnakeGame.WIDTH
                || newHead.x < 0
                || newHead.y >= SnakeGame.HEIGHT
                || newHead.y < 0) {
            isAlive = false;
            return;
        }

        if (checkCollision(newHead)) {
            isAlive = false;
            return;
        }

        snakeParts.add(0, newHead);

        if (newHead.x == apple.x && newHead.y == apple.y) {
            apple.isAlive = false;
        } else {
            removeTail();
        }
    }

    public GameObject createNewHead() {
        GameObject go;
        switch (direction){
            case UP: go = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y-1);
            break;

            case DOWN: go = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y+1);
            break;

            case LEFT: go = new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y);
            break;

            default: go = new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
        }
        return go;
    }

    public void removeTail() {
       int indexOfLastElement = snakeParts.size() - 1;
       snakeParts.remove(indexOfLastElement);
    }

    public boolean checkCollision(GameObject object) {
        for (GameObject part : snakeParts) {
            if (part.x == object.x && part.y == object.y) {
                return true;
            }
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }


}
