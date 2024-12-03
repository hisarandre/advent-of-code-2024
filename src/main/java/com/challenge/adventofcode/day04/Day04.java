package com.challenge.adventofcode.day04;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;

public class Day04 {

    public static void solve() throws IOException {
        String inputFile = "input04.txt";
        String fileContent = InputHelper.readInput(inputFile);
        int sum = code(fileContent, false);
        System.out.println("The solution is: " + sum);
    }

    public static int code(String fileContent, Boolean isPartOne) {
        int sum = 0;

        String[] lines = fileContent.split("\n");

        return sum;
    }
}
