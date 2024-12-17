package com.challenge.adventofcode.twentyFour.day12;

import com.challenge.adventofcode.helper.InputHelper;
import com.challenge.adventofcode.twentyFour.Solver;

import java.io.IOException;
import java.util.*;

public class Day12 extends Solver {

    public Day12() {
        super("input12.txt",false );
    }

    public int code(String fileContent, Boolean isPartOne) throws IOException {
        int sum = 0;
        String[] lines = fileContent.split("\n");
        String[][] map = InputHelper.convertToMapToString(lines);

        return findAllGarden(lines, map, isPartOne);
    }

    public static int findAllGarden(String[] lines, String[][] map, boolean isPartOne){

        int sum = 0;
        List<Region> regions = findRegions(map);

        if (isPartOne) {
            for (Region region : regions) {
                sum += region.getArea() * region.getPerimeter();
            }
        } else {

            for (Region region : regions) {

                int[][] letters = convertToGrid(map.length, map[0].length, region.getPositions());
                int sides = 0;

                sides += checkY(letters);
                sides += checkX(letters);

                sum += sides * region.getArea();
            }


        }

        return sum;
    }

    public static int[][] convertToGrid(int rows, int cols, int[][] positions){
        int[][] grid = new int[rows][cols];

        for (int[] pos : positions) {
            int x = pos[0];
            int y = pos[1];

            if (x >= 0 && x < rows && y >= 0 && y < cols) {
                grid[x][y] = 1;
            }
        }

        return grid;
    }

    public static int checkX(int[][] grid) {
        int sides = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for (int x = 0; x < rows; x++) {
            boolean isTopSegment = false;
            boolean isBottomSegment = false;

            for (int y = 0; y < cols; y++) {
                if (grid[x][y] == 1) {

                    if (x == 0 || grid[x - 1][y] != 1) {

                        if (!isTopSegment) {
                            sides++;
                            isTopSegment = true;
                        }
                    } else {
                        isTopSegment = false;
                    }
                } else {
                    isTopSegment = false;
                }

                if (grid[x][y] == 1) {
                    if (x == rows - 1 || grid[x + 1][y] != 1) {
                        if (!isBottomSegment) {
                            sides++;
                            isBottomSegment = true;
                        }
                    } else {
                        isBottomSegment = false;
                    }
                } else {
                    isBottomSegment = false;
                }
            }
        }

        return sides;
    }
    public static int checkY(int[][] grid) {
        int sides = 0;
        int rows = grid.length;
        int cols = grid[0].length;

        for (int y = 0; y < cols; y++) {
            boolean isLeftSegment = false;
            boolean isRightSegment = false;

            for (int x = 0; x < rows; x++) {
                if (grid[x][y] == 1) {
                    // Check left side
                    if (y == 0 || grid[x][y - 1] == 0) {
                        if (!isLeftSegment) {
                            sides++;
                            isLeftSegment = true;
                        }
                    } else {
                        isLeftSegment = false;
                    }

                    // Check right side
                    if (y == cols - 1 || grid[x][y + 1] == 0) {
                        if (!isRightSegment) {
                            sides++;
                            isRightSegment = true;
                        }
                    } else {
                        isRightSegment = false;
                    }
                } else {
                    // Reset segments when encountering a gap
                    isLeftSegment = false;
                    isRightSegment = false;
                }
            }
        }
        return sides;
    }



    public static int[][] getContours(String[][] input, String target) {
        int rows = input.length;
        int cols = input[0].length;
        int[][] result = new int[rows][cols];

        int[][] directions = {
                {-1, 0}, {1, 0}, {0, -1}, {0, 1}
        };

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (input[i][j].equals(target)) {
                    boolean isContour = false;

                    // Vérifier les voisins dans les 4 directions pour voir si c'est un contour
                    for (int[] dir : directions) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];

                        // Vérifier si on sort du cadre ou si le voisin n'est pas du type cible
                        if (ni < 0 || ni >= rows || nj < 0 || nj >= cols || !input[ni][nj].equals(target)) {
                            isContour = true;
                            break;
                        }
                    }

                    // Si c'est un contour, on marque le résultat avec 1
                    result[i][j] = isContour ? 1 : 0;
                } else {
                    result[i][j] = 0;
                }
            }
        }

        // Marquer comme 1 les régions internes qui sont entourées de la même lettre (à l'intérieur des contours)
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                if (input[i][j].equals(target)) {
                    boolean surroundedBySame = true;

                    // Vérifier si les 4 voisins sont aussi du type cible
                    for (int[] dir : directions) {
                        int ni = i + dir[0];
                        int nj = j + dir[1];

                        // Si un voisin n'est pas du même type, ce n'est pas une région interne
                        if (ni < 0 || ni >= rows || nj < 0 || nj >= cols || !input[ni][nj].equals(target)) {
                            surroundedBySame = false;
                            break;
                        }
                    }

                    // Si les voisins sont tous du même type, on marque la cellule comme une partie de la région interne
                    if (surroundedBySame) {
                        result[i][j] = 1;
                    }
                }
            }
        }

        return result;
    }


    public static List<Region> findRegions(String[][] map) {
        List<Region> regions = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        int rows = map.length;
        int cols = map[0].length;

        // Directions for moving up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Start DFS from each cell
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                String regionType = map[x][y];
                if (!visited.contains(x + "," + y)) {

                    String[][] resultMap = new String[rows][cols];
                    for (int i = 0; i < rows; i++) {
                        Arrays.fill(resultMap[i], ".");
                    }

                    int[] result = dfs(map, x, y, regionType, visited, resultMap, directions);
                    int area = result[0];
                    int perimeter = result[1];
                    Region region = new Region(createPositions(resultMap, regionType), regionType, area, perimeter);
                    regions.add(region);
                }
            }
        }

        return regions;
    }

    public static int[] dfs(String[][] map, int x, int y, String regionType, Set<String> visited, String[][] resultMap, int[][] directions) {
        if (x < 0 || x >= map.length || y < 0 || y >= map[0].length || !map[x][y].equals(regionType)) {
            return new int[]{0, 0};
        }

        if (visited.contains(x + "," + y)) {
            return new int[]{0, 0};
        }
        visited.add(x + "," + y);

        resultMap[x][y] = "1";

        int area = 1;
        int perimeter = 0;

        for (int[] dir : directions) {
            int nx = x + dir[0];
            int ny = y + dir[1];

            if (nx < 0 || nx >= map.length || ny < 0 || ny >= map[0].length || !map[nx][ny].equals(regionType)) {
                perimeter++;
            } else {
                int[] result = dfs(map, nx, ny, regionType, visited, resultMap, directions);
                area += result[0];
                perimeter += result[1];
            }
        }
        return new int[]{area, perimeter};
    }

    public static int[][] createPositions(String[][] resultMap, String regionType) {
        List<int[]> positionsList = new ArrayList<>();
        for (int i = 0; i < resultMap.length; i++) {
            for (int j = 0; j < resultMap[0].length; j++) {
                if (resultMap[i][j].equals("1")) {
                    positionsList.add(new int[]{i, j});
                }
            }
        }
        return positionsList.toArray(new int[0][0]);
    }

}
