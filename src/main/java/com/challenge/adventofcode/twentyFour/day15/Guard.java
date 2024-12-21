package com.challenge.adventofcode.twentyFour.day15;

import com.challenge.adventofcode.commun.Direction;

public class Guard {

    int positionX;
    int positionY;
    Direction direction;

    public Guard(int positionX, int positionY, Direction direction) {
        this.positionX = positionX;
        this.positionY = positionY;
        this.direction = direction;
    }

    public Guard() {
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }

    public Direction getDirection() {
       return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int calculateNextY(Guard guard){
        return switch (guard.getDirection()) {
            case Direction.N ->
                    guard.getPositionY() - 1;
            case Direction.S ->
                    guard.getPositionY() + 1;
            default -> guard.getPositionY();
        };
    }

    public int calculateNextX(Guard guard){
        return switch (guard.getDirection()) {
            case Direction.W->
                    guard.getPositionX() - 1;
            case Direction.E ->
                    guard.getPositionX() + 1;
            default -> guard.getPositionX();
        };
    }

    public Direction turn(Guard guard) {
        return switch (guard.getDirection()) {
            case N -> Direction.E;
            case E -> Direction.S;
            case S -> Direction.W;
            case W -> Direction.N;
        };
    }

    public Guard reset(int x, int y, Direction direction) {
        setPositionX(x);
        setPositionY(y);
        setDirection(direction);
        return this;
    }
    
}