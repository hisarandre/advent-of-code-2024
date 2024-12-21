package com.challenge.adventofcode.twentyFour.day15;

public class Obstruction {

    int positionX;
    int positionY;

    int leftX;
    int leftY;
    int rightX;
    int rightY;


    public Obstruction(int positionX, int positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public Obstruction( int leftX, int leftY,int rightX, int rightY) {
        this.rightX = rightX;
        this.rightY = rightY;
        this.leftX = leftX;
        this.leftY = leftY;
    }


    public int getLeftX() {
        return leftX;
    }

    public void setLeftX(int leftX) {
        this.leftX = leftX;
    }

    public int getLeftY() {
        return leftY;
    }

    public void setLeftY(int leftY) {
        this.leftY = leftY;
    }

    public int getRightX() {
        return rightX;
    }

    public void setRightX(int rightX) {
        this.rightX = rightX;
    }

    public int getRightY() {
        return rightY;
    }

    public void setRightY(int rightY) {
        this.rightY = rightY;
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

}
