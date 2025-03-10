package it.unibo.copilot.core.boundary;

import it.unibo.copilot.core.control.GameController;
import it.unibo.copilot.core.control.GameState;

/**
 * GameBoundary interface for the UI
 */
public interface GameBoundary {
    void initialize(GameState gameState);
    void update(GameState gameState);
    void showGameOver(GameState gameState);
    void showVictory(GameState gameState);
    void setController(GameController controller);
}

