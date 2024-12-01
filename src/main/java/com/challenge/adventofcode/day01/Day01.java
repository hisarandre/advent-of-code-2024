package com.challenge.adventofcode.day01;

import com.challenge.adventofcode.helper.InputHelper;
import java.io.IOException;
import java.util.*;

public class Day01 {

    public static void solve() throws IOException {
        String inputFile = "input01.txt";
        String fileContent = InputHelper.readInput(inputFile);
        int sum = code(fileContent, false);
        System.out.println("The solution is: " + sum);
    }

    public static int code(String fileContent, Boolean isPartOne) {
        int sum = 0;
        String[] lines = fileContent.split("\n");
        List<Integer> rightNb = new ArrayList<>();
        List<Integer> leftNb = new ArrayList<>();

        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                String[] numbers = line.trim().split("\\s+");
                int left = Integer.parseInt(numbers[0]);
                int right = Integer.parseInt(numbers[1]);
                leftNb.add(left);
                rightNb.add(right);
            }
        }

        List<Integer> rightNbSorted = rightNb.stream()
                .sorted()
                .toList();

        List<Integer> leftNbSorted = leftNb.stream()
                .sorted()
                .toList();

        if (isPartOne) {
            sum = calculateDifference(rightNbSorted, leftNbSorted, sum);
        } else {
            sum = calculateSimularity(rightNbSorted, leftNbSorted, sum);
        }

        return sum;
    }

    public static int calculateDifference(List<Integer> rightNbSorted, List<Integer>  leftNbSorted, int sum) {
        for (int i = 0; i < rightNbSorted.size(); i++) {
            int difference = rightNbSorted.get(i) - leftNbSorted.get(i);
            sum += Math.abs(difference);
        }
        return sum;
    }

    public static int calculateSimularity(List<Integer> rightNbSorted, List<Integer>  leftNbSorted, int sum) {
        for (Integer integer : rightNbSorted) {
            long count = leftNbSorted.stream()
                    .filter(n -> n.equals(integer))
                    .count();
            sum += (int) (integer * count);
        }
        return sum;
    }
}
