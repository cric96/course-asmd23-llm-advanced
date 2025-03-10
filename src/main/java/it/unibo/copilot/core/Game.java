package it.unibo.copilot.core;

import it.unibo.copilot.core.boundary.*;
import it.unibo.copilot.core.boundary.GameBoundary;
import it.unibo.copilot.core.control.*;
/**
 * This is the game of Snake!
 *  The game is played on a grid board, where the snake moves around and eats food.
 *  The snake grows longer as it eats food, and the game ends when the snake runs into itself or the wall.
 *  The goal is to get the highest score possible by eating as much food as possible.
 *  The game is controlled by the user using the arrow keys.
 *  The game is over when the snake runs into itself or the wall.
 *  The score is the number of food items eaten.
 *  The game is won when the snake eats all the food on the board.
 *  The game is lost when the snake runs into itself or the wall.
 */

public class Game {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameConfig config = new GameConfig(20, 20, 20, 150);
            GameBoundary view = new SwingGameBoundary();
            GameController controller = new GameController(config, view);
            controller.start();
        });
    }
}