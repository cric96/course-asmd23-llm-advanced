package it.unibo.copilot.core.boundary;

import it.unibo.copilot.core.entity.*;
import it.unibo.copilot.core.control.*;
import javax.swing.*;

/**
 * SwingGameBoundary implements the Boundary using Swing
 */
public class SwingGameBoundary implements GameBoundary {
    private GameController controller;
    private GameState gameState;
    private JFrame frame;
    private GamePanel gamePanel;
    private JLabel scoreLabel;

    @Override
    public void initialize(GameState gameState) {
        this.gameState = gameState;

        if (frame == null) {
            createFrame();
        } else {
            // Reset existing components
            gamePanel.setGameState(gameState);
            scoreLabel.setText("Score: 0");
            frame.repaint();
        }
    }

    private void createFrame() {
        frame = new javax.swing.JFrame("Snake Game");
        frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);

        GameConfig config = gameState.getConfig();
        gamePanel = new GamePanel(gameState);

        // Create score panel
        javax.swing.JPanel scorePanel = new javax.swing.JPanel();
        scoreLabel = new javax.swing.JLabel("Score: 0");
        scorePanel.add(scoreLabel);

        // Set layout
        frame.setLayout(new java.awt.BorderLayout());
        frame.add(gamePanel, java.awt.BorderLayout.CENTER);
        frame.add(scorePanel, java.awt.BorderLayout.SOUTH);

        // Set keyboard listener
        frame.addKeyListener(new SnakeKeyListener());
        frame.setFocusable(true);

        // Size and display
        int width = config.getBoardWidth() * config.getCellSize();
        int height = config.getBoardHeight() * config.getCellSize();
        gamePanel.setPreferredSize(new java.awt.Dimension(width, height));
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);  // Center on screen
        frame.setVisible(true);
    }

    @Override
    public void update(GameState gameState) {
        this.gameState = gameState;
        scoreLabel.setText("Score: " + gameState.getScore());
        gamePanel.repaint();
    }

    @Override
    public void showGameOver(GameState gameState) {
        javax.swing.JOptionPane.showMessageDialog(
                frame,
                "Game Over! Your score: " + gameState.getScore(),
                "Game Over",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void showVictory(GameState gameState) {
        javax.swing.JOptionPane.showMessageDialog(
                frame,
                "Congratulations! You won! Score: " + gameState.getScore(),
                "Victory",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }

    @Override
    public void setController(GameController controller) {
        this.controller = controller;
    }

    /**
     * Inner class for the game canvas
     */
    private class GamePanel extends javax.swing.JPanel {
        private GameState gameState;

        public GamePanel(GameState gameState) {
            this.gameState = gameState;
            setBackground(java.awt.Color.BLACK);
        }

        public void setGameState(GameState gameState) {
            this.gameState = gameState;
        }

        @Override
        protected void paintComponent(java.awt.Graphics g) {
            super.paintComponent(g);

            GameConfig config = gameState.getConfig();
            int cellSize = config.getCellSize();

            // Draw snake
            g.setColor(java.awt.Color.GREEN);
            for (Position p : gameState.getSnake().getBody()) {
                g.fillRect(p.getX() * cellSize, p.getY() * cellSize, cellSize, cellSize);
            }

            // Draw head with different color
            Position head = gameState.getSnake().getHead();
            g.setColor(java.awt.Color.YELLOW);
            g.fillRect(head.getX() * cellSize, head.getY() * cellSize, cellSize, cellSize);

            // Draw food
            g.setColor(java.awt.Color.RED);
            Position foodPos = gameState.getFood().getPosition();
            g.fillOval(foodPos.getX() * cellSize, foodPos.getY() * cellSize, cellSize, cellSize);

            // Draw grid (optional)
            g.setColor(java.awt.Color.DARK_GRAY);
            for (int x = 0; x < config.getBoardWidth(); x++) {
                for (int y = 0; y < config.getBoardHeight(); y++) {
                    g.drawRect(x * cellSize, y * cellSize, cellSize, cellSize);
                }
            }
        }
    }

    /**
     * Inner class for key handling
     */
    private class SnakeKeyListener extends java.awt.event.KeyAdapter {
        @Override
        public void keyPressed(java.awt.event.KeyEvent e) {
            switch (e.getKeyCode()) {
                case java.awt.event.KeyEvent.VK_UP:
                    controller.handleDirectionChange(Direction.UP);
                    break;
                case java.awt.event.KeyEvent.VK_DOWN:
                    controller.handleDirectionChange(Direction.DOWN);
                    break;
                case java.awt.event.KeyEvent.VK_LEFT:
                    controller.handleDirectionChange(Direction.LEFT);
                    break;
                case java.awt.event.KeyEvent.VK_RIGHT:
                    controller.handleDirectionChange(Direction.RIGHT);
                    break;
                case java.awt.event.KeyEvent.VK_R:
                    controller.restart();
                    break;
            }
        }
    }
}

