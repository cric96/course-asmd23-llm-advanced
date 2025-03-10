package it.unibo.copilot.core.entity;

public class Food {
    private final Position position;

    public Food(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}