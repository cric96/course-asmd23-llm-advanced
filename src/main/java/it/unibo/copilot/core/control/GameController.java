package it.unibo.copilot.core.control;

import it.unibo.copilot.core.boundary.GameBoundary;
import it.unibo.copilot.core.entity.Direction;
import it.unibo.copilot.core.entity.Food;
import it.unibo.copilot.core.entity.Position;

public class GameController {
    private final GameState gameState;
    private final GameBoundary view;

    public GameController(GameConfig config, GameBoundary view) {
        this.gameState = new GameState(config);
        this.view = view;
        this.view.setController(this);
    }

    public void start() {
        initializeTimer();
        view.initialize(gameState);
    }

    public void handleDirectionChange(Direction direction) {
        gameState.setDirection(direction);
    }

    public void restart() {
        // Create a new game state with the same config
        GameState newState = new GameState(gameState.getConfig());
        // Update reference
        gameState.setGameOver();
        // Reinitialize view
        view.initialize(newState);
    }

    private void initializeTimer() {
        int delay = gameState.getConfig().getUpdateIntervalMs();
        new javax.swing.Timer(delay, e -> updateGame()).start();
    }

    private void updateGame() {
        if (gameState.isGameOver()) {
            return;
        }

        // Move snake
        gameState.getSnake().move(gameState.getCurrentDirection());

        // Check wall collision
        Position head = gameState.getSnake().getHead();
        GameConfig config = gameState.getConfig();
        if (head.getX() < 0 || head.getX() >= config.getBoardWidth() ||
                head.getY() < 0 || head.getY() >= config.getBoardHeight()) {
            gameState.setGameOver();
            view.showGameOver(gameState);
            return;
        }

        // Check self collision
        if (gameState.getSnake().collidesWithSelf()) {
            gameState.setGameOver();
            view.showGameOver(gameState);
            return;
        }

        // Check food collision
        if (head.equals(gameState.getFood().getPosition())) {
            gameState.incrementScore();
            gameState.getSnake().grow();
            gameState.setFood(generateFood());

            // Check win condition
            if (gameState.isGameWon()) {
                gameState.setGameOver();
                view.showVictory(gameState);
                return;
            }
        }

        // Update view
        view.update(gameState);
    }

    private Food generateFood() {
        java.util.Random random = new java.util.Random();
        GameConfig config = gameState.getConfig();
        int x, y;
        Position position;

        do {
            x = random.nextInt(config.getBoardWidth());
            y = random.nextInt(config.getBoardHeight());
            position = new Position(x, y);
        } while (gameState.getSnake().occupies(position));

        return new Food(position);
    }
}
