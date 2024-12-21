package com.challenge.adventofcode.twentyFour.day15;

import com.challenge.adventofcode.commun.Position;
import com.challenge.adventofcode.twentyFour.Solver;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.StreamSupport;

public class Day15 extends Solver {

    public Day15() {
        super("input15.txt",false );
    }

    public int code(String fileContent, Boolean isPartOne) {
        String[] splitLines = fileContent.split("\n\n");
        String mapSection = splitLines[0];
        String instructionsSection = splitLines[1];

        String[] mapLines = mapSection.split("\n");
        String instructions = instructionsSection.replaceAll("\n", "");


        if (isPartOne) {
            return codePartOne(mapLines, instructions);
        }

        return codePartTwo(mapLines, instructions);
    }

    private int codePartTwo(String[] mapLines, String instructions) {

        String[] mapTransformed = transformMap(mapLines);

        Map.Entry<Plan, Guard> result = convertToPlanAndGuard(mapTransformed);
        Plan plan = result.getKey();
        Guard guard = result.getValue();

        for (char instruction : instructions.toCharArray()) {
            Position move = getNextPosition(instruction);
            if (move == null) continue;
            printMap(plan, mapTransformed.length,  mapTransformed[0].length() , guard);

            int nextX = guard.getPositionX() + move.getPositionX();
            int nextY = guard.getPositionY() + move.getPositionY();

            // Check if next position hits a wall
            if (checkWalls(plan.getWalls(), nextX, nextY)) {
                continue;
            }

            // Check if next position has a box
            if (checkBigBoxs(plan.getObstructions(), nextX, nextY)) {
                if (tryPushBoxChain(plan, move, nextX, nextY, isPartOne)) {
                    // If boxes were pushed, move guard
                    guard.setPositionX(nextX);
                    guard.setPositionY(nextY);
                }
            } else {
                // No box, move guard
                guard.setPositionX(nextX);
                guard.setPositionY(nextY);
            }


        }


        return calculateGPSSum(plan.getObstructions(), isPartOne);
    }

    private String[] transformMap (String[] mapLines){

        String[] mapTransformed = new String[mapLines.length];

        for (int y = 0; y < mapLines.length; y++) {
            char[] line = mapLines[y].toCharArray();
            char[] newLine = new char[line.length * 2];

            for (int x = 0; x < line.length; x++) {
                switch (line[x]) {
                    case '.' -> {
                        newLine[x * 2] = '.';
                        newLine[x * 2 + 1] = '.';
                    }
                    case '#' -> {
                        newLine[x * 2] = '#';
                        newLine[x * 2 + 1] = '#';
                    }
                    case 'O' -> {
                        newLine[x * 2] = '[';
                        newLine[x * 2 + 1] = ']';
                    }
                    case '@' -> {
                        newLine[x * 2] = '@';
                        newLine[x * 2 + 1] = '.';
                    }
                }
            }

            mapTransformed[y] = new String(newLine);
        }
        return mapTransformed;
    }

    private int codePartOne(String[] mapLines, String instructions){

        Map.Entry<Plan, Guard> result = convertToPlanAndGuard(mapLines);
        Plan plan = result.getKey();
        Guard guard = result.getValue();

        for (char instruction : instructions.toCharArray()) {
            Position move = getNextPosition(instruction);
            if (move == null) continue;

            int nextX = guard.getPositionX() + move.getPositionX();
            int nextY = guard.getPositionY() + move.getPositionY();

            // Check if next position hits a wall
            if (checkWalls(plan.getWalls(), nextX, nextY)) {
                continue;
            }

            // Check if next position has a box
            if (checkBoxs(plan.getObstructions(), nextX, nextY)) {
                // Try to push
                if (tryPushBoxChain(plan, move, nextX, nextY, isPartOne)) {
                    // If boxes were pushed, move guard
                    guard.setPositionX(nextX);
                    guard.setPositionY(nextY);
                }
            } else {
                // No box, move guard
                guard.setPositionX(nextX);
                guard.setPositionY(nextY);
            }
        }

        return calculateGPSSum(plan.getObstructions(), isPartOne);
    }

    private boolean tryPushBoxChain(Plan plan, Position move, int startX, int startY, boolean isPartOne) {
        List<Obstruction> boxChain = new ArrayList<>();
        Set<Obstruction> processedBoxes = new HashSet<>();

        List<Position> currentPositions = new ArrayList<>();

        while (true) {
            int currentX, currentY;
            if (boxChain.isEmpty()) {
                currentX = startX;
                currentY = startY;
            } else {
                Obstruction lastBox = boxChain.get(boxChain.size() - 1);
                // Determine which side was hit and get the appropriate next position
                if (move.getPositionX() < 0) { // Moving left
                    currentX = lastBox.getLeftX() + move.getPositionX();
                    currentY = lastBox.getLeftY();
                } else if (move.getPositionX() > 0) { // Moving right
                    currentX = lastBox.getRightX() + move.getPositionX();
                    currentY = lastBox.getRightY();
                } else if (move.getPositionY() < 0) { // Moving up
                    currentX = lastBox.getLeftX();
                    currentY = lastBox.getLeftY() + move.getPositionY();
                } else { // Moving down
                    currentX = lastBox.getRightX();
                    currentY = lastBox.getRightY() + move.getPositionY();
                }
            }

            // Find boxes at the current position that are in the movement path
            List<Obstruction> currentBoxes = new ArrayList<>();
            List<Obstruction> boxesToCheck = plan.getObstructions().stream()
                    .filter(box -> !processedBoxes.contains(box) &&
                            isBoxInMovementPath(box, currentX, currentY, move))
                    .toList();

            // Add initial boxes and their connected boxes
            for (Obstruction box : boxesToCheck) {
                if (!processedBoxes.contains(box)) {
                    currentBoxes.add(box);
                    processedBoxes.add(box);

                    // If moving vertically, find all horizontally connected boxes
                    if (move.getPositionY() != 0) {
                        addConnectedBoxes(box, currentBoxes, processedBoxes, plan.getObstructions());
                    }
                }
            }

            if (currentBoxes.isEmpty()) {
                // No box here - this is where the chain would end
                if (!boxChain.isEmpty()) {
                    // Move all boxes in the chain
                    for (Obstruction box : boxChain) {
                        box.setLeftX(box.getLeftX() + move.getPositionX());
                        box.setLeftY(box.getLeftY() + move.getPositionY());
                        box.setRightX(box.getRightX() + move.getPositionX());
                        box.setRightY(box.getRightY() + move.getPositionY());
                    }
                    return true;
                }
                return false;
            }

            System.out.println("found " + currentBoxes.size() + " boxes");

            // Add all found boxes to the chain
            boxChain.addAll(currentBoxes);

            if (isPartOne) {
                if (checkWalls(plan.getWalls(), currentX, currentY)) {
                    return false;
                }
            } else {
                currentBoxes.forEach(p -> {
                    System.out.println("--------");
                    System.out.println(p.getLeftX() + move.getPositionX() + ", " +
                            p.getLeftY() + move.getPositionY());
                    System.out.println(p.getRightX() + move.getPositionX() + ", " +
                            p.getRightY() + move.getPositionY());
                    System.out.println("--------");
                });

                // Use the new wall check method with all current boxes
                if (checkBigBoxWalls(plan.getWalls(), currentBoxes, move)) {
                    return false;
                }
            }
        }
    }

// Helper method to determine if a box is in the movement path
        private boolean isBoxInMovementPath(Obstruction box, int x, int y, Position move) {
            if (move.getPositionX() < 0) { // Moving left
                return (box.getRightX() == x && box.getRightY() == y) ||
                        (box.getLeftX() == x && box.getLeftY() == y);
            } else if (move.getPositionX() > 0) { // Moving right
                return (box.getLeftX() == x && box.getLeftY() == y) ||
                        (box.getRightX() == x && box.getRightY() == y);
            } else if (move.getPositionY() < 0) { // Moving up
                return (box.getRightX() == x && box.getRightY() == y) ||
                        (box.getLeftX() == x && box.getLeftY() == y);
            } else { // Moving down
                return (box.getLeftX() == x && box.getLeftY() == y) ||
                        (box.getRightX() == x && box.getRightY() == y);
            }
        }

// Helper method to find and add horizontally connected boxes
private void addConnectedBoxes(Obstruction box, List<Obstruction> currentBoxes,
                               Set<Obstruction> processedBoxes, List<Obstruction> allBoxes) {
    // Check for boxes connected horizontally - sharing either left or right position
    allBoxes.stream()
            .filter(b -> !processedBoxes.contains(b))
            .filter(b -> {
                // Check if boxes share a y-coordinate (are on same horizontal line)
                boolean sameY = b.getLeftY() == box.getLeftY();
                if (!sameY) return false;

                // Check if boxes are touching
                return Math.abs(b.getLeftX() - box.getRightX()) <= 1 || // b is to the right of box
                        Math.abs(box.getLeftX() - b.getRightX()) <= 1;   // b is to the left of box
            })
            .forEach(connectedBox -> {
                currentBoxes.add(connectedBox);
                processedBoxes.add(connectedBox);
                // Recursively check for more connected boxes
                addConnectedBoxes(connectedBox, currentBoxes, processedBoxes, allBoxes);
            });
}

/*
    private boolean tryPushBoxChain(Plan plan, Position move, int startX, int startY, boolean isPartOne) {
        List<Obstruction> boxChain = new ArrayList<>();
        Set<Obstruction> processedBoxes = new HashSet<>();
        int currentX = startX;
        int currentY = startY;

        while (true) {
            int finalCurrentX = currentX;
            int finalCurrentY = currentY;

            List<Obstruction> currentBoxes;

            if (isPartOne) {
                currentBoxes = plan.getObstructions().stream()
                        .filter(box -> box.getPositionX() == finalCurrentX && box.getPositionY() == finalCurrentY)
                        .toList();
            } else {
                currentBoxes = plan.getObstructions().stream()
                        .filter(box -> !processedBoxes.contains(box) &&
                                (box.getLeftX() == finalCurrentX || box.getLeftY() == finalCurrentY ||
                                        box.getRightX() == finalCurrentX || box.getRightY() == finalCurrentY))
                        .toList();
            }

            if (currentBoxes.isEmpty()) {
                // No box here - this is where the chain would end
                // If we found at least one box and hit empty space, we can move the chain
                if (!boxChain.isEmpty()) {
                    // Move all boxes in the chain
                    for (Obstruction box : boxChain) {
                        box.setLeftX(box.getLeftX() + move.getPositionX());
                        box.setLeftY(box.getLeftY() + move.getPositionY());
                        box.setRightX(box.getRightX() + move.getPositionX());
                        box.setRightY(box.getRightY() + move.getPositionY());
                    }
                    return true;
                }
                return false;
            }

            System.out.println("found " + currentBoxes.size() + " boxes");

            // Add all found boxes to the chain
            for (Obstruction box : currentBoxes) {
                boxChain.add(box);
                processedBoxes.add(box);

                // Determine the next position to check based on which side we hit
                if (box.getLeftX() == currentX || box.getLeftY() == currentY) {
                    currentX = box.getRightX() + move.getPositionX();
                    currentY = box.getRightY() + move.getPositionY();
                } else {
                    currentX = box.getLeftX() + move.getPositionX();
                    currentY = box.getLeftY() + move.getPositionY();
                }
            }


            if(isPartOne){
                if (checkWalls(plan.getWalls(), currentX, currentY)) {
                    return false;
                }
            }else {

                currentBoxes.forEach(p-> {
                    System.out.println("--------");

                    System.out.println(p.getLeftX() + move.getPositionX() + ", " + p.getLeftY() + move.getPositionY());
                    System.out.println(p.getRightX() + move.getPositionX() + ", " + p.getRightY() + move.getPositionY());

                    System.out.println("--------");

                });

                if (checkBigBoxWalls(plan.getWalls(), currentX, currentY)) {
                    return false;
                }
            }

        }
    }
*/

/*
    private boolean tryPushBoxChain(Plan plan, Position move, int startX, int startY, boolean isPartOne) {
        List<Obstruction> boxChain = new ArrayList<>();
        Set<Obstruction> processedBoxes = new HashSet<>();
        int currentX = startX;
        int currentY = startY;

        while (true) {
            int finalCurrentX = currentX;
            int finalCurrentY = currentY;

            List<Obstruction> currentBoxes;

            if (isPartOne) {
                currentBoxes = plan.getObstructions().stream()
                        .filter(box -> box.getPositionX() == finalCurrentX && box.getPositionY() == finalCurrentY)
                        .toList();
            } else {
                currentBoxes = plan.getObstructions().stream()
                        .filter(box -> !processedBoxes.contains(box) &&
                                (box.getLeftX() == finalCurrentX || box.getLeftY() == finalCurrentY ||
                                        box.getRightX() == finalCurrentX || box.getRightY() == finalCurrentY))
                        .toList();
            }

            if (currentBoxes.isEmpty()) {
                // No box here - this is where the chain would end
                // If we found at least one box and hit empty space, we can move the chain
                if (!boxChain.isEmpty()) {
                    // Move all boxes in the chain
                    for (Obstruction box : boxChain) {
                        box.setLeftX(box.getLeftX() + move.getPositionX());
                        box.setLeftY(box.getLeftY() + move.getPositionY());
                        box.setRightX(box.getRightX() + move.getPositionX());
                        box.setRightY(box.getRightY() + move.getPositionY());
                    }
                    return true;
                }
                return false;
            }

            System.out.println("found " + currentBoxes.size() + " boxes");

            // Add all found boxes to the chain
            for (Obstruction box : currentBoxes) {
                boxChain.add(box);
                processedBoxes.add(box);

                // Determine the next position to check based on which side we hit
                if (box.getLeftX() == currentX || box.getLeftY() == currentY) {
                    currentX = box.getRightX() + move.getPositionX();
                    currentY = box.getRightY() + move.getPositionY();
                } else {
                    currentX = box.getLeftX() + move.getPositionX();
                    currentY = box.getLeftY() + move.getPositionY();
                }
            }


            if(isPartOne){
                if (checkWalls(plan.getWalls(), currentX, currentY)) {
                    return false;
                }
            }else {

                currentBoxes.forEach(p-> {
                    System.out.println("--------");

                    System.out.println(p.getLeftX() + move.getPositionX() + ", " + p.getLeftY() + move.getPositionY());
                    System.out.println(p.getRightX() + move.getPositionX() + ", " + p.getRightY() + move.getPositionY());

                    System.out.println("--------");

                });

                if (checkBigBoxWalls(plan.getWalls(), currentX, currentY)) {
                    return false;
                }
            }

        }
    }
*/

    private int calculateGPSSum(List<Obstruction> boxes, boolean isPartOne) {
        int sum = 0;
        for (Obstruction box : boxes) {
            if (isPartOne){
                sum += (100 * box.getPositionY()) + box.getPositionX();
            } else {
                int closestX = Math.min(box.getLeftX(), box.getRightX());
                int closestY = Math.min(box.getLeftY(), box.getRightY());

                sum += (100 * closestY) + closestX;
            }
        }
        return sum;
    }

    private Position getNextPosition(char instruction) {
        return switch (instruction) {
            case '^' -> new Position(0, -1);
            case 'v' -> new Position(0, 1);
            case '>' -> new Position(1, 0);
            case '<' -> new Position(-1, 0);
            default -> null;
        };
    }

    private boolean checkBoxs(List<Obstruction> boxes, int x, int y) {
        return boxes.stream().anyMatch(box ->
                box.getPositionX() == x && box.getPositionY() == y);
    }


    private boolean checkBigBoxWalls(List<Wall> walls, List<Obstruction> boxesToMove, Position move) {
        // Check if any box in the chain would hit a wall after moving
        for (Obstruction box : boxesToMove) {
            int nextLeftX = box.getLeftX() + move.getPositionX();
            int nextLeftY = box.getLeftY() + move.getPositionY();
            int nextRightX = box.getRightX() + move.getPositionX();
            int nextRightY = box.getRightY() + move.getPositionY();

            // Check if either end of the box would hit a wall
            if (walls.stream().anyMatch(wall ->
                    (wall.getPositionX() == nextLeftX && wall.getPositionY() == nextLeftY) ||
                            (wall.getPositionX() == nextRightX && wall.getPositionY() == nextRightY))) {
                return true;
            }
        }
        return false;
    }

    private boolean checkWalls(List<Wall> walls, int x, int y) {
        return walls.stream().anyMatch(wall ->
                wall.getPositionX() == x && wall.getPositionY() == y);
    }

    private boolean checkBigBoxWalls(List<Wall> walls, int x, int y) {

        System.out.println("WALLS : " + x + ", " + y);
        return walls.stream().anyMatch(wall ->
                wall.getPositionX() == x && wall.getPositionY() == y);
    }

    private Map.Entry<Plan, Guard> convertToPlanAndGuard(String[] mapLines) {
        Guard guard = new Guard();
        List<Obstruction> obstructions = new ArrayList<>();
        List<Wall> walls = new ArrayList<>();

        for (int y = 0; y < mapLines.length; y++) {
            char[] line = mapLines[y].toCharArray();
            for (int x = 0; x < line.length; x++) {

                switch (line[x]) {
                    case '@' -> {
                        guard.setPositionX(x);
                        guard.setPositionY(y);
                    }
                    case '#' -> walls.add(new Wall(x, y));
                }

                if(isPartOne){
                    switch (line[x]) {
                        case 'O' -> obstructions.add(new Obstruction(x, y));
                    }
                } else {
                    switch (line[x]) {
                        case '[' -> {
                            obstructions.add(new Obstruction(x, y, x+1, y));
                        }
                    }
                }

            }
        }

        return new AbstractMap.SimpleEntry<>(
                new Plan(obstructions, walls),
                guard
        );
    }

    public void printMap(Plan plan, int height, int width, Guard guard) {
        char[][] grid = new char[height][width];

        // Initialize empty grid
        for (int i = 0; i < height; i++) {
            Arrays.fill(grid[i], '.');
        }

        // Add walls
        for (Wall wall : plan.getWalls()) {
            grid[wall.getPositionY()][wall.getPositionX()] = '#';
        }

        // Add boxes
        for (Obstruction box : plan.getObstructions()) {
            grid[box.getRightY()][box.getRightX()] = ']';
            grid[box.getLeftY()][box.getLeftX()] = '[';
        }

        // Add guard
        grid[guard.getPositionY()][guard.getPositionX()] = '@';

        // Print grid
        for (char[] row : grid) {
            System.out.println(new String(row));
        }
        System.out.println();
    }
}
