package it.unibo.copilot.core.control;

/**
 * GameConfig entity for storing game configuration
 */
public class GameConfig {
    private final int boardWidth;
    private final int boardHeight;
    private final int cellSize;
    private final int updateIntervalMs;

    public GameConfig(int boardWidth, int boardHeight, int cellSize, int updateIntervalMs) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.cellSize = cellSize;
        this.updateIntervalMs = updateIntervalMs;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public int getCellSize() {
        return cellSize;
    }

    public int getUpdateIntervalMs() {
        return updateIntervalMs;
    }
}