package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.util.Arrays;

public class Day04 extends Solver {

    public Day04() {
        super("input04.txt",true );
    }

    public int code(String fileContent, Boolean isPartOne) {
        int sum = 0;
        String[] lines = fileContent.split("\n");
        int rows = lines.length;
        int cols = lines[0].length();

        char[][] map = convertToMap(lines, rows, cols);
        char[] xmas = {'X', 'M', 'A', 'S'};
        char[] xmasReversed = {'S', 'A', 'M', 'X'};

        if (isPartOne){
            sum = sum + calculateHorizontal(map, xmas, rows, cols);
            sum = sum + calculateHorizontal(map, xmasReversed, rows, cols);

            sum = sum + calculateVertical(map, xmas, rows, cols);
            sum = sum + calculateVertical(map, xmasReversed, rows, cols);

            sum = sum + calculateDiagonal(map, xmas, rows, cols);
            sum = sum + calculateDiagonal(map, xmasReversed, rows, cols);
        } else {
            sum = calculateXmas(map, rows, cols);
        }

        return sum;
    }

    public static int calculateXmas(char[][] map, int rows, int cols) {
        int sum = 0;

        for (int row = 1; row < rows - 1; row++) {
            for (int col = 1; col < cols - 1; col++) {
                char letter = map[row][col];

                if (letter == 'A'){
                    int count = 0;

                    char topLeft = map[row - 1][col - 1];
                    char topRight = map[row - 1][col + 1];
                    char bottomLeft = map[row + 1][col - 1];
                    char bottomRight = map[row + 1][col + 1];

                    if ((topLeft == 'M' && bottomRight == 'S') || (topLeft == 'S' && bottomRight == 'M')) {
                        count++;
                    }
                    if ((topRight == 'M' && bottomLeft == 'S') || (topRight == 'S' && bottomLeft == 'M')) {
                        count++;
                    }

                    if (count == 2){
                        sum++;
                    }
                }
            }
        }
        return sum;
    }


    public static int calculateDiagonal(char[][] map, char[] xmasOrReverse, int rows, int cols) {
        int sum = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                char letter = map[row][col];

                if (letter == xmasOrReverse[0]) {
                    sum += checkDiagonal(map, xmasOrReverse, row, col, rows, cols,  1);
                    sum += checkDiagonal(map, xmasOrReverse, row, col, rows, cols,  -1);
                }
            }
        }
        return sum;
    }

    private static int checkDiagonal(char[][] map, char[] xmasOrReverse, int row, int col, int rows, int cols, int colIncrement) {
        int xmasIndex = 1;

        for (int i = 1; i < 4; i++) {
            int newRow = row + i ;
            int newCol = col + i * colIncrement;

            if (newRow > rows - 1 || newCol > cols - 1 || newCol < 0) {
                break;
            }

            char letter = map[newRow][newCol];
            if (letter == xmasOrReverse[xmasIndex]) {
                xmasIndex++;
            }
        }
        return xmasIndex == xmasOrReverse.length ? 1 : 0;
    }

    public static int calculateHorizontal(char[][] map, char[] xmasOrReverse, int rows, int cols) {
        return calculateDirection(map, xmasOrReverse, rows, cols, true);
    }

    public static int calculateVertical(char[][] map, char[] xmasOrReverse, int rows, int cols) {
        return calculateDirection(map, xmasOrReverse, rows, cols, false);
    }

    private static int calculateDirection(char[][] map, char[] xmasOrReverse, int rows, int cols, boolean isHorizontal) {
        int sum = 0;

        for (int i = 0; i < (isHorizontal ? rows : cols); i++) {
            int xmasIndex = 0;

            for (int j = 0; j < (isHorizontal ? cols : rows); j++) {
                char letter = isHorizontal ? map[i][j] : map[j][i];

                if (letter == xmasOrReverse[0]) {
                    xmasIndex = 1;
                } else if (letter == xmasOrReverse[xmasIndex]) {
                    xmasIndex++;

                    if (xmasIndex == xmasOrReverse.length) {
                        sum++;
                        xmasIndex = 0;
                    }
                } else {
                    xmasIndex = 0;
                }
            }
        }
        return sum;
    }

    public static char[][] convertToMap (String[] lines, int rows, int cols){
        char[][] map = new char[rows][cols];
        for (int l = 0; l < rows; l++) {
            map[l] = lines[l].toCharArray();
        }
        return map;
    }
}
