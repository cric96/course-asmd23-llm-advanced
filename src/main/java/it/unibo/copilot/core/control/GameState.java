package it.unibo.copilot.core.control;

import it.unibo.copilot.core.entity.Direction;
import it.unibo.copilot.core.entity.Snake;
import it.unibo.copilot.core.entity.Food;
import it.unibo.copilot.core.entity.Position;

import java.util.Random;

public class GameState {
    private final Snake snake;
    private Food food;
    private int score;
    private Direction currentDirection;
    private boolean isGameOver;
    private final GameConfig config;

    public GameState(GameConfig config) {
        this.config = config;
        this.snake = new Snake(new Position(config.getBoardWidth() / 2, config.getBoardHeight() / 2));
        this.food = generateFood();
        this.score = 0;
        this.currentDirection = Direction.RIGHT;
        this.isGameOver = false;
    }

    public Snake getSnake() {
        return snake;
    }

    public Food getFood() {
        return food;
    }

    public int getScore() {
        return score;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public GameConfig getConfig() {
        return config;
    }

    public void setDirection(Direction newDirection) {
        if (!currentDirection.isOpposite(newDirection)) {
            currentDirection = newDirection;
        }
    }

    public void incrementScore() {
        score++;
    }

    public void setGameOver() {
        isGameOver = true;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    private Food generateFood() {
        Random random = new Random();
        int x, y;
        Position position;

        do {
            x = random.nextInt(config.getBoardWidth());
            y = random.nextInt(config.getBoardHeight());
            position = new Position(x, y);
        } while (snake.occupies(position));

        return new Food(position);
    }

    public boolean isGameWon() {
        return snake.getLength() == config.getBoardWidth() * config.getBoardHeight() - 1;
    }
}