package com.challenge.adventofcode.twentyFour.day06;

import java.util.List;

public class Plan {

    public List<Obstruction> obstructions;
    public int borderLimitX;
    public int borderLimitY;

    public Plan(List<Obstruction> obstructions, int borderLimitX, int borderLimitY ) {
        this.obstructions = obstructions;
        this.borderLimitX = borderLimitX;
        this.borderLimitY = borderLimitY;
    }

    public List<Obstruction> getObstructions() {
        return obstructions;
    }

    public void setObstructions(List<Obstruction> obstructions) {
        this.obstructions = obstructions;
    }

    public int getBorderLimitX() {
        return borderLimitX;
    }

    public void setBorderLimitX(int borderLimitX) {
        this.borderLimitX = borderLimitX;
    }

    public int getBorderLimitY() {
        return borderLimitY;
    }

    public void setBorderLimitY(int borderLimitY) {
        this.borderLimitY = borderLimitY;
    }

    public boolean isNextPositionAvailable(int nextX, int nextY){
        List<Obstruction> obstructions = this.getObstructions();

        for (Obstruction obstruction : obstructions) {
            if (obstruction.getPositionX() == nextX && obstruction.getPositionY() == nextY) {
                return false;
            }
        }
        return true;
    }

    public boolean isNextPositionOutOfMap(int nextX, int nextY){
        return nextY == -1 || nextY == this.getBorderLimitY() + 1 || nextX == -1 || nextX == this.getBorderLimitX() + 1;
    }

    public void addObstruction(Obstruction obstruction) {
        this.obstructions.add(obstruction);
    }

    public void removeObstruction(Obstruction obstruction) {
        this.obstructions.remove(obstruction);
    }

}