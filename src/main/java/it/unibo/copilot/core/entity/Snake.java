package it.unibo.copilot.core.entity;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.LinkedList;


public class Snake {
    private final Deque<Position> body;
    private boolean shouldGrow;

    public Snake(Position initialPosition) {
        this.body = new LinkedList<>();
        this.body.addFirst(initialPosition);
        this.shouldGrow = false;
    }

    public Position getHead() {
        return body.getFirst();
    }

    public List<Position> getBody() {
        return new ArrayList<>(body);
    }

    public int getLength() {
        return body.size();
    }

    public void grow() {
        this.shouldGrow = true;
    }

    public void move(Direction direction) {
        Position newHead = getHead().translate(direction);
        body.addFirst(newHead);

        if (!shouldGrow) {
            body.removeLast();
        } else {
            shouldGrow = false;
        }
    }

    public boolean occupies(Position position) {
        return body.contains(position);
    }

    public boolean collidesWithSelf() {
        Position head = getHead();
        return body.stream()
                .skip(1) // Skip head
                .anyMatch(p -> p.equals(head));
    }
}
