package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;
import com.challenge.adventofcode.twentyFour.day06.Obstruction;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day08 extends Solver{

    public Day08() {
        super("input08.txt",true );
    }

    public int code(String fileContent, Boolean isPartOne) throws IOException {
        int sum = 0;
        String[] lines = fileContent.split("\n");
        int yMaxBorder = lines.length - 1;
        int xMaxBorder = lines[0].length() - 1;
        List<int[]> antinodes = new ArrayList<>();

        Map<String, List<int[]>> categorizedCoordinates = generateCoordinates(lines);

        for (Map.Entry<String, List<int[]>> frequencyAndCoord : categorizedCoordinates.entrySet()) {
            List<int[]> coords = frequencyAndCoord.getValue();

            for (int i = 0; i < coords.size(); i++) {
                int[] current = coords.get(i);

                for (int j = i + 1; j < coords.size(); j++) {
                    int[] next = coords.get(j);

                    int diffX = next[0] - current[0];
                    int diffY = next[1] - current[1];

                    if(isPartOne){
                        sum += createNode(current, next, diffY, diffX, xMaxBorder, yMaxBorder, antinodes, coords);
                    } else {

                    }
                }
            }
        }
        return sum;
    }

    public static int createNode(int[] current, int[] next, int diffY, int diffX, int xMaxBorder, int yMaxBorder, List<int[]> antinodes, List<int[]> coords){
        int sum = 0;

        int currentAntinodeX = current[0] - diffX;
        int currentAntinodeY = current[1] - diffY;

        int nextAntinodeX = next[0] + diffX;
        int nextAntinodeY = next[1] + diffY;

        int[] currentAntinode = {currentAntinodeX, currentAntinodeY};
        int[] nextAntinode = {nextAntinodeX, nextAntinodeY};

        if(checkIfValid(currentAntinode,xMaxBorder, yMaxBorder, antinodes, coords)){
            antinodes.add(currentAntinode);
            sum++;
        }

        if(checkIfValid(nextAntinode,xMaxBorder, yMaxBorder, antinodes, coords)){
            antinodes.add(nextAntinode);
            sum++;
        }

        return sum;
    }
    public static boolean checkIfValid(int[] antinode, int xMaxBorder, int yMaxBorder, List<int[]> antinodes, List<int[]> coords ) {
        return isInsideMap(antinode[0], antinode[1], xMaxBorder, yMaxBorder) && !containsArray(antinodes, antinode) && !containsArray(coords, antinode);
    }

    public static boolean isInsideMap(int nodeX, int nodeY, int xMaxBorder, int yMaxBorder) {
        return nodeX >= 0 && nodeX <= xMaxBorder &&
                nodeY >= 0 && nodeY <= yMaxBorder;
    }

    public static boolean containsArray(List<int[]> list, int[] array) {
        for (int[] item : list) {
            if (Arrays.equals(item, array)) {
                return true;
            }
        }
        return false;
    }

    public Map<String, List<int[]>> generateCoordinates(String[] lines) {
        Map<String, List<int[]>> categorizedCoordinates = new HashMap<>();

        for (int y = 0; y < lines.length; y++) {
            char[] letters = lines[y].toCharArray();

            for (int x = 0; x < letters.length; x++) {
                String letter = Character.toString(letters[x]);

                if (!letter.matches("[a-zA-Z0-9]")) {
                    continue;
                }

                categorizedCoordinates.putIfAbsent(letter, new ArrayList<>());
                categorizedCoordinates.get(letter).add(new int[]{x, y});
            }
        }
        return categorizedCoordinates;
    }
}
