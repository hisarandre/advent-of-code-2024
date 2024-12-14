package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.util.*;

public class Day10 extends Solver{

    public Day10() {
        super("input10.txt",false );
    }

    public int code(String fileContent, Boolean isPartOne) throws IOException {
        int sum = 0;
        String[] lines = fileContent.split("\n");

        int[][] map = InputHelper.convertToMap(lines);

        return findAllPaths(map, isPartOne);
    }


    public int findAllPaths(int[][] map, boolean isPartOne){
        int score = 0;

        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j] == 0) {
                    score += bfs(map, i, j, isPartOne);
                }
            }
        }

        return score;
    }

    public static int bfs(int[][] map, int startX, int startY, boolean isPartOne) {
        int rows = map.length;
        int cols = map[0].length;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startX, startY, 0});

        Set<String> visited = new HashSet<>();
        visited.add(startX + "," + startY);

        int validPaths = 0;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int x = current[0];
            int y = current[1];
            int height = current[2];

            if (height == 9) {
                validPaths++;
                continue;
            }

            for (int[] dir : directions) {
                int newX = x + dir[0];
                int newY = y + dir[1];

                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                    int newHeight = map[newX][newY];

                    if (newHeight == height + 1 && !isPartOne) {
                        queue.add(new int[]{newX, newY, newHeight});
                    }

                    if (newHeight == height + 1 && !visited.contains(newX + "," + newY) && isPartOne) {
                        queue.add(new int[]{newX, newY, newHeight});
                        visited.add(newX + "," + newY);
                    }
                }
            }
        }
        return validPaths;
    }
}
