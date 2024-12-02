package com.challenge.adventofcode.day03;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.util.Arrays;

public class Day03 {

    public static void solve() throws IOException {
        String inputFile = "input03.txt";
        String fileContent = InputHelper.readInput(inputFile);
        int sum = code(fileContent, true);
        System.out.println("The solution is: " + sum);
    }

    public static int code(String fileContent, Boolean isPartOne) {

        int sum = 0;
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            String[] numbers = line.trim().split("\\s+");
        }
        return sum;
    }
}
