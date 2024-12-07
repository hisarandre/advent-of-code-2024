package com.challenge.adventofcode.twentyFour.day06;
import com.challenge.adventofcode.twentyFour.Solver;

import java.io.IOException;
import java.util.*;

public class Day06 extends Solver {

    public Day06() {
        super("input06.txt", false);
    }

    public int code(String fileContent, Boolean isPartOne) throws IOException {
        String[] lines = fileContent.split("\n");
        Map.Entry<Plan, Guard> result = convertToMapAndGuard(lines);
        Plan plan = result.getKey();
        Guard guard = result.getValue();
        int sum = 0;

        if (isPartOne){
            return calculateGuardDistinctPosition(guard, plan);
        }

        List<Obstruction> newObstructions = generateNewObstructions(lines);

        int startX = guard.getPositionX();
        int startY = guard.getPositionY();
        Direction startDirection = guard.getDirection();

        for (Obstruction newObstruction : newObstructions) {
            plan.addObstruction(newObstruction);
            guard = guard.reset(startX, startY, startDirection);

            boolean isStuck = moveGuardInLoop(guard, plan);
            if (isStuck) sum++;

            plan.removeObstruction(newObstruction);
        }
        return sum;
    }

    public static List<Obstruction> generateNewObstructions(String[] lines) {
        List<Obstruction> newObstructions = new ArrayList<>();
        for (int y = 0; y < lines.length; y++) {
            char[] letters = lines[y].toCharArray();
            for (int x = 0; x < letters.length; x++) {
                if (letters[x] == '.') {
                    Obstruction obstruction = new Obstruction(x, y);
                    newObstructions.add(obstruction);
                }
            }
        }
        return newObstructions;
    }



    public static boolean moveGuardInLoop(Guard guard, Plan plan) {
        Map<Position, Direction> visitedPositions = new HashMap<>();
        Position startingPosition = new Position(guard.getPositionX(), guard.getPositionY());
        Direction startDirection = guard.getDirection();
        visitedPositions.put(startingPosition, startDirection);

        while (true) {
            int nextX = guard.calculateNextX(guard);
            int nextY = guard.calculateNextY(guard);

            if (plan.isNextPositionOutOfMap(nextX, nextY)) {
                return false; //stop here
            }

            Position newPosition = new Position(nextX, nextY);
            Direction currentDirection = guard.getDirection();

            if (visitedPositions.containsKey(newPosition) &&
                    visitedPositions.get(newPosition).equals(currentDirection)) {
                return true; // is valid
            }

            visitedPositions.put(newPosition, currentDirection);

            if (plan.isNextPositionAvailable(nextX, nextY)) {
                guard.setPositionX(nextX);
                guard.setPositionY(nextY);
            } else {
                guard.setDirection(guard.turn(guard));
            }
        }
    }

    public static int calculateGuardDistinctPosition(Guard guard, Plan plan){
        Set<String> visitedPos = new HashSet<>();
        visitedPos.add(guard.getPositionX() + "," + guard.getPositionY());

        int sum = 1;
        while (true) {
            int nextX = guard.calculateNextX(guard);
            int nextY = guard.calculateNextY(guard);

            boolean isOutOfMap = plan.isNextPositionOutOfMap(nextX, nextY);

            if (isOutOfMap) {
                break;
            }

            boolean isPathClear = plan.isNextPositionAvailable(nextX, nextY);
            if (isPathClear) {
                guard.setPositionY(nextY);
                guard.setPositionX(nextX);

                boolean alreadyVisited = visitedPos.contains(nextX + "," + nextY);

                if(!alreadyVisited){
                    visitedPos.add(guard.getPositionX() + "," + guard.getPositionY());
                    sum++;
                }

            } else {
                guard.setDirection(guard.turn(guard));
            }
        }
        return sum;
    }

    public static Map.Entry<Plan, Guard> convertToMapAndGuard(String[] lines){
        int yMaxBorder = lines.length - 1;
        int xMaxBorder = lines[0].length() - 1;

        Guard guard = new Guard();
        List<Obstruction> obstructions = new ArrayList<>();

        for(int y = 0; y < lines.length ; y++){

            char[] letters = lines[y].toCharArray();

            for (int x = 0; x < lines[0].length() ; x++){
                char letter = letters[x];

                switch (letter) {
                    case '#':
                        Obstruction obstruction = new Obstruction(x, y);
                        obstructions.add(obstruction);
                        break;
                    case '^': // North
                        guard.setPositionX(x);
                        guard.setPositionY(y);
                        guard.setDirection(Direction.N);
                        break;
                    case 'v': // South
                        guard.setPositionX(x);
                        guard.setPositionY(y);
                        guard.setDirection(Direction.S);
                        break;
                    case '<': // West
                        guard.setPositionX(x);
                        guard.setPositionY(y);
                        guard.setDirection(Direction.W);
                        break;
                    case '>': // East
                        guard.setPositionX(x);
                        guard.setPositionY(y);
                        guard.setDirection(Direction.E);
                        break;
                }
            }
        }

        Plan plan = new Plan(obstructions, xMaxBorder, yMaxBorder);
        return new AbstractMap.SimpleEntry<>(plan, guard);
    }
}


