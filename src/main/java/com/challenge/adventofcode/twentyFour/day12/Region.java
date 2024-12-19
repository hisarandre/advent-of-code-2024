package com.challenge.adventofcode.twentyFour.day12;

import com.challenge.adventofcode.commun.Direction;
import com.challenge.adventofcode.commun.Position;

public class Region {

    int[][] positions;

    String type;

    int area;

    int perimeter;

    public Region(int[][] positions, String type, int area, int perimeter) {
        this.positions = positions;
        this.type = type;
        this.area = area;
        this.perimeter = perimeter;
    }


    public int[][] getPositions() {
        return positions;
    }

    public void setPositions(int[][] positions) {
        this.positions = positions;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public int getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(int perimeter) {
        this.perimeter = perimeter;
    }

    public Direction checkNextDirection(Position position, Position nextPosition) {
        if (position.getPositionX() == nextPosition.getPositionX()) {
            return position.getPositionY() > nextPosition.getPositionY() ? Direction.N : Direction.S;
        } else if (position.getPositionY() == nextPosition.getPositionY()) {
            return position.getPositionX() > nextPosition.getPositionX() ? Direction.W : Direction.E;
        }

        return null;
    }
}

