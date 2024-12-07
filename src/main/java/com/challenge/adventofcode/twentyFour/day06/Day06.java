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

        int yMaxBorder = lines.length - 1;
        int xMaxBorder = lines[0].length() - 1;

        if (isPartOne){
            int sum = 1;

            Map.Entry<Plan, Guard> result = convertToMapAndGuard(xMaxBorder, yMaxBorder, lines);
            Plan plan = result.getKey();
            Guard guard = result.getValue();

            sum = calculateGuardDistinctPosition(guard, plan);
            return sum;
        }

        int sum = 0;

        Map.Entry<Plan, Guard> result = convertToMapAndGuard(xMaxBorder, yMaxBorder, lines);
        Plan plan = result.getKey();
        Guard guard = result.getValue();
        List<Obstruction> newObstructions = createNewObstructions(lines);

        int startX = guard.getPositionX();
        int startY = guard.getPositionY();
        Direction startDirection = guard.getDirection();

        for (Obstruction newObstruction : newObstructions) {

            plan.addObstruction(newObstruction);
            guard = guard.reset(startX, startY, startDirection);

            System.out.println("obs: " + newObstruction.getPositionY());

            boolean isProbability = moveGuardTillOriginalPosition(guard, plan);

            if (isProbability){
                System.out.println("ADD ONE");
                sum ++;
            }

            plan.removeObstruction(newObstruction);
        }


        return sum;
    }

    public static List<Obstruction> createNewObstructions(String[] lines) {
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



    public static boolean moveGuardTillOriginalPosition(Guard guard, Plan plan) {
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

    public static Map.Entry<Plan, Guard> convertToMapAndGuard(int xMaxBorder, int yMaxBorder, String[] lines){
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


