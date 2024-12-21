package com.challenge.adventofcode.twentyFour.day15;

import java.util.List;

public class Plan {

    public List<Obstruction> obstructions;
    public List<Wall> walls;

    public Plan(List<Obstruction> obstructions, List<Wall> walls ) {
        this.obstructions = obstructions;
        this.walls = walls;
    }

    public List<Obstruction> getObstructions() {
        return obstructions;
    }

    public void setObstructions(List<Obstruction> obstructions) {
        this.obstructions = obstructions;
    }

    public List<Wall> getWalls() {
        return walls;
    }

    public void setWalls(List<Wall> walls) {
        this.walls = walls;
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


    public void addObstruction(Obstruction obstruction) {
        this.obstructions.add(obstruction);
    }

    public void removeObstruction(Obstruction obstruction) {
        this.obstructions.remove(obstruction);
    }

    public void addWall(Wall wall) {
        this.walls.add(wall);
    }

    public void removeWall(Wall wall) {
        this.walls.remove(wall);
    }


}