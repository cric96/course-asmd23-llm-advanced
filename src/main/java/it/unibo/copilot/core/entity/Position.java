package it.unibo.copilot.core.entity;

public class Position {
    private final int x;
    private final int y;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Position translate(Direction direction) {
        switch (direction) {
            case UP: return new Position(x, y - 1);
            case DOWN: return new Position(x, y + 1);
            case LEFT: return new Position(x - 1, y);
            case RIGHT: return new Position(x + 1, y);
            default: throw new IllegalArgumentException("Invalid direction");
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Position)) return false;
        Position other = (Position) obj;
        return x == other.x && y == other.y;
    }

    @Override
    public int hashCode() {
        return 31 * x + y;
    }
}
