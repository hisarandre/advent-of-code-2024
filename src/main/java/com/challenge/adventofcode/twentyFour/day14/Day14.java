package com.challenge.adventofcode.twentyFour.day14;

import com.challenge.adventofcode.commun.Position;
import com.challenge.adventofcode.helper.InputHelper;
import com.challenge.adventofcode.twentyFour.Solver;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day14 extends Solver {

    public Day14() {
        super("input14.txt",false );
    }

    public int code(String fileContent, Boolean isPartOne) throws IOException {
        int tilesWide = 101; // x
        int tilesTall = 103; // y
        int[][] map = new int[tilesTall][tilesWide];

        List<Robot> robots = convertToRobots(fileContent);

        if (isPartOne){
           return codePartOne(robots, tilesWide, tilesTall, map);
        } else {
            return codePartTwo(robots, tilesWide, tilesTall);
        }
    }

    public static int codePartOne(List<Robot> robots, int tilesWide, int tilesTall, int[][] map ){
        int secs = 100;

        for (int s = 0; s < secs; s++) {
            robots.forEach(robot -> robot.move(tilesWide - 1, tilesTall - 1));
        }

        robots.forEach(robot -> {
            Position position = robot.getPosition();
            map[position.getPositionY()][position.getPositionX()]++;
        });

        return calculateSafetyFactor(tilesWide, tilesTall, map);
    }

    public static int codePartTwo(List<Robot> robots, int tilesWide, int tilesTall ){
        int sec = 0;

        while(true){
            int[][] map = new int[tilesTall][tilesWide];
            sec++;

            robots.forEach(robot -> robot.move(tilesWide - 1, tilesTall - 1));

            robots.forEach(robot -> {
                Position position = robot.getPosition();
                map[position.getPositionY()][position.getPositionX()] = 1;
            });

            if (findTree(map)){
                for (int y = 0; y < tilesTall; y++) {
                    for (int x = 0; x < tilesWide; x++) {
                        if (map[y][x] == 1) {
                            System.out.print("1 ");
                        } else {
                            System.out.print(". ");
                        }
                    }
                    System.out.println();
                }

                break;
            }
        }

        return sec;
    }

    public static boolean findTree(int[][] map){

        int[][] treePattern = {
                {0, 0, 0, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 0},
                {1, 1, 1, 1, 1, 1, 1},
        };

        int rows = map.length;
        int cols = map[0].length;

        for (int y = 0; y <= rows ; y++) {
            for (int x = 0; x <= cols ; x++) {
                if (bfsCheckPattern(map, treePattern, y, x)) {
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean bfsCheckPattern(int[][] map, int[][] pattern, int startY, int startX) {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startY, startX});

        int patternRows = pattern.length;
        int patternCols = pattern[0].length;

        for (int y = 0; y < patternRows; y++) {
            for (int x = 0; x < patternCols; x++) {

                int mapY = startY + y;
                int mapX = startX + x;

                if (mapY >= map.length || mapX >= map[0].length) {
                    return false;
                }

                if (!(map[mapY][mapX] == pattern[y][x])) {
                    return false;
                }
            }
        }

        return true;
    }

    public static int calculateSafetyFactor(int tilesWide, int tilesTall, int[][] map){
        int topLeft = 0;
        int topRight = 0;
        int bottomLeft = 0;
        int bottomRight = 0;

        for (int y = 0; y < tilesTall; y++) {
            for (int x = 0; x < tilesWide; x++) {

                if (x == tilesWide / 2 || y == tilesTall / 2) {
                    continue;
                }

                if (map[y][x] > 0) {
                    int count = map[y][x];
                    if (x < tilesWide / 2 && y < tilesTall / 2) topLeft += count;
                    if (x >= tilesWide / 2 && y < tilesTall / 2) topRight += count;
                    if (x < tilesWide / 2 && y >= tilesTall / 2) bottomLeft += count;
                    if (x >= tilesWide / 2 && y >= tilesTall / 2) bottomRight += count;
                }
            }
        }

        return topLeft * topRight * bottomLeft * bottomRight;
    }

    public static List<Robot> convertToRobots(String fileContent){
        String[] lines = fileContent.split("\n");

        return Arrays.stream(lines)
                .map(line -> {
                    String[] parts = line.split(" ");

                    String[] positionParts = parts[0].substring(2).split(",");
                    Position position = new Position(Integer.parseInt(positionParts[0]), Integer.parseInt(positionParts[1]));

                    String[] velocityParts = parts[1].substring(2).split(",");
                    Position velocity = new Position(Integer.parseInt(velocityParts[0]), Integer.parseInt(velocityParts[1]));

                    return new Robot(position, velocity);
                })
                .toList();
    }
}
