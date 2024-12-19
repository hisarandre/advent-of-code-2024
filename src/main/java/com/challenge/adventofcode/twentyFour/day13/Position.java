package com.challenge.adventofcode.twentyFour.day13;


import java.math.BigInteger;

public class Position {
    private BigInteger positionX;
    private BigInteger positionY;

    public Position(BigInteger positionX, BigInteger positionY) {
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public BigInteger getPositionX() {
        return positionX;
    }

    public void setPositionX(BigInteger positionX) {
        this.positionX = positionX;
    }

    public BigInteger getPositionY() {
        return positionY;
    }

    public void setPositionY(BigInteger positionY) {
        this.positionY = positionY;
    }

}
