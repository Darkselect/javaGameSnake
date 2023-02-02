package com.javarush.games.snake;

import com.javarush.engine.cell.*;

public class SnakeGame extends Game {
    public static final int WIDTH = 15;
    public static final int HEIGHT = 15;
    private Snake snake;
    private int turnDelay;
    private Apple apple;

    private boolean isGameStopped;
    private static final int GOAL = 28;

    private int score;

    @Override
    public void initialize() {
        setScreenSize(WIDTH, HEIGHT);
        createGame();


    }

    private void createGame() {
        Snake snake = new Snake(WIDTH / 2, HEIGHT / 2);
        this.snake = snake;
        createNewApple();
        isGameStopped = false;
        drawScene();
        turnDelay = 300;
        setTurnTimer(turnDelay);

        score = 0;
        setScore(score);
    }

    private void drawScene() {
        for (int x = 0; x < WIDTH; x ++) {
            for (int y = 0; y < HEIGHT; y++) {
                setCellValueEx(x, y, Color.DARKSEAGREEN, "");

            }
        }
        snake.draw(this);
        apple.draw(this);
    }

    @Override
    public void onTurn(int x) {
        if (!apple.isAlive) {
            createNewApple();

            score += 5;
            setScore(score);
            turnDelay -= 10;
            setTurnTimer(this.turnDelay);
        }
        snake.move(apple);

        if (!snake.isAlive) {
            gameOver();
        }
        if (GOAL < snake.getLength()) {
            win();
        }

        drawScene();


    }

    @Override
    public void onKeyPress(Key key) {
        if (key.equals(Key.LEFT)) {
            snake.setDirection(Direction.LEFT);
        } else if (key.equals(Key.RIGHT)) {
            snake.setDirection(Direction.RIGHT);
        } else if (key.equals(Key.UP)) {
            snake.setDirection(Direction.UP);
        } else if (key.equals(Key.DOWN)) {
            snake.setDirection(Direction.DOWN);
        } else if ((key.equals(Key.SPACE) && isGameStopped)) {
            createGame();
        }
    }

    private void createNewApple() {
        int firstNum = getRandomNumber(HEIGHT);
        int secondNum = getRandomNumber(WIDTH);
        Apple apple = new Apple(firstNum, secondNum);
        this.apple = apple;
        while (snake.checkCollision(apple))
            apple = new Apple(getRandomNumber(WIDTH),getRandomNumber(HEIGHT));

    }

    private void gameOver() {
        stopTurnTimer();
        isGameStopped = true;
        showMessageDialog(Color.AQUA, "Axaxa LOSHARA", Color.GREEN, 75);
    }

    private void win() {
        isGameStopped = true;
        stopTurnTimer();
        showMessageDialog(Color.GREEN, "You Win, Krasavchik", Color.BLUE, 75);

    }

}
