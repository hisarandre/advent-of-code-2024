package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;

public abstract class Solver {
    protected String inputFile;
    protected boolean isPartOne;

    public Solver(String inputFile, boolean isPartOne ) {
        this.inputFile = inputFile;
        this.isPartOne = isPartOne;
    }

    public abstract int code(String fileContent, Boolean isPartOne) throws IOException;

    public void solve() throws IOException {
        String fileContent = InputHelper.readInput(inputFile);
        int sum = code(fileContent, isPartOne);
        System.out.println("The solution is: " + sum);
    }
}
