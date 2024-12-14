package com.challenge.adventofcode.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InputHelper {

    public static String readInput(String filename) throws IOException {
        return Files.readString(Paths.get("src/main/resources/inputs/" + filename));
    }

    public static int[][] convertToMap(String[] lines){
        int rows = lines.length;
        int cols = lines[0].length();
        int[][] map = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                map[i][j] = lines[i].charAt(j) - '0';
            }
        }
        return map;
    }

    public static String[][] convertToMapToString(String[] lines){
        int rows = lines.length;
        int cols = lines[0].length();
        String[][] map = new String[rows][cols];
        for (int i = 0; i < rows; i++) {
            map[i] = lines[i].split("");
        }
        return map;
    }
}
