package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.util.*;

public class Day12 extends Solver{

    public Day12() {
        super("input12.txt",false );
    }

    public int code(String fileContent, Boolean isPartOne) throws IOException {
        int sum = 0;
        String[] lines = fileContent.split("\n");
        String[][] map = InputHelper.convertToMapToString(lines);

        return findAllGarden(map, isPartOne);
    }

    public static int findAllGarden(String[][] map, boolean isPartOne){
        Map<String, List<int[]>> regionData = new HashMap<>();

        Set<String> visited = new HashSet<>();
        int totalPrice = 0;

        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map[0].length; y++) {
                String regionType = map[x][y];
                int[] result = dfs(map, x, y, regionType, visited);
                int area = result[0];
                int perimeter = result[1];
                totalPrice += area * perimeter;

                regionData.putIfAbsent(regionType, new ArrayList<>());
                regionData.get(regionType).add(new int[]{x, y});
            }
        }

        if (isPartOne) {
            return totalPrice;
        } else {
            return calculateSides(regionData);
        }
    }

    public static int calculateSides(Map<String, List<int[]>> regionData) {

        return 0;
    }


    public static int[] dfs(String[][] map, int x, int y, String regionType, Set<String> visited) {

        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length || !map[x][y].equals(regionType)) {
            return new int[]{0, 1};
        }

        if (visited.contains(x + "," + y)) {
            return new int[]{0, 0};
        }

        visited.add(x + "," + y);
        System.out.println(x + "," + y);

        int area = 1;
        int perimeter = 0;
        int sides = 0;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int[] dir : directions) {
            int[] result = dfs(map, x + dir[0], y + dir[1], regionType, visited);
            area += result[0];
            perimeter += result[1];
        }

        return new int[]{area, perimeter};
    }

}
