package com.challenge.adventofcode.twentyFour.day14;

import com.challenge.adventofcode.commun.Position;

public class Robot {

    Position position;

    Position velocity;

    public Robot(Position position, Position velocity) {
        this.position = position;
        this.velocity = velocity;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getVelocity() {
        return velocity;
    }

    public void setVelocity(Position velocity) {
        this.velocity = velocity;
    }

    public void move(int maxX, int maxY) {
        int newX = position.getPositionX() + velocity.getPositionX();
        int newY = position.getPositionY() + velocity.getPositionY();

        if (newX > maxX) {
            newX = newX - maxX - 1;
        } else if (newX < 0) {
            newX = maxX + newX + 1;
        }

        if (newY > maxY) {
            newY = newY - maxY - 1;
        } else if (newY < 0) {
            newY = maxY + newY + 1;
        }

        position.setPositionX(newX);
        position.setPositionY(newY);
    }
}
