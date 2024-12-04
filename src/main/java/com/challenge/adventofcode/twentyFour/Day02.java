package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {

    public static void solve() throws IOException {
        String inputFile = "input02.txt";
        String fileContent = InputHelper.readInput(inputFile);
        int sum = code(fileContent, false);
        System.out.println("The solution is: " + sum);
    }

    public static int code(String fileContent, Boolean isPartOne) {
        int sum = 0;
        String[] lines = fileContent.split("\n");

        for (String line : lines) {

            String[] numbers = line.trim().split("\\s+");

            List<Integer> report = Arrays.stream(numbers)
                    .map(Integer::parseInt)
                    .toList();

            Boolean safe = false;

            for (int i = 0; i < report.size(); i++) {
                safe = isSafe(report);
            }

            if (!isPartOne && !safe){
                for (int i = 0; i < report.size() ; i++) {
                    List<Integer> newReport = new ArrayList<>(report);
                    newReport.remove(i);
                    safe = isSafe(newReport);
                    if (safe) break;
                }
            }

            if (safe) sum++;
        }
        return sum;
    }

    public static Boolean isSafe(List<Integer> report) {
        boolean safe = true;
        boolean isIncreasing = report.get(0) < report.get(1);

        for (int i = 0; i < report.size() - 1; i++) {
            int difference = Math.abs(report.get(i) - report.get(i + 1));

            if (difference < 1 || difference > 3) {
                safe = false;
                break;
            }

            if (isIncreasing && report.get(i) >= report.get(i + 1)) {
                safe = false;
                break;
            }

            if (!isIncreasing && report.get(i) <= report.get(i + 1)) {
                safe = false;
                break;
            }
        }
        return safe;
    }
}
