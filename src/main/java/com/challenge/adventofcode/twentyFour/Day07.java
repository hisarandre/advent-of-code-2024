package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day07 {

    public void solve() throws IOException {
        String fileContent = InputHelper.readInput("input07.txt");
        BigInteger sum = code(fileContent, false);
        System.out.println("The solution is: " + sum);
    }

    public BigInteger code(String fileContent, Boolean isPartOne) throws IOException {
        BigInteger sum = BigInteger.ZERO;
        String[] lines = fileContent.split("\n");

        for (String line : lines) {
            String[] nbsAndResult = line.split(" ");
            BigInteger res = new BigInteger(nbsAndResult[0].substring(0, nbsAndResult[0].length() - 1));

            List<BigInteger> nbs = Arrays.stream(nbsAndResult)
                    .skip(1)
                    .map(BigInteger::new)
                    .toList();

            List<String[]> operations = generateOperations(nbs, isPartOne);

            for (String[] ops : operations) {
                BigInteger result = calculateOperations(nbs, ops);

                if (result.equals(res)) {
                    sum = sum.add(res);
                    break;
                }
            }
        }
        return sum;
    }

    public static List<String[]> generateOperations(List<BigInteger> nbs, boolean isPartOne) {
        List<String[]> result = new ArrayList<>();
        int numOperators = nbs.size() - 1;
        String[] operators = {"+", "*"};

        if(!isPartOne){
            operators = new String[]{"+", "*", "||"};
        }

        generateComboOperations(result, new String[numOperators], operators, 0);
        return result;
    }

    private static void generateComboOperations(List<String[]> result, String[] current, String[] operators, int index) {
        if (index == current.length) {
            result.add(current.clone());
            return;
        }

        for (String operator : operators) {
            current[index] = operator;
            generateComboOperations(result, current, operators, index + 1);
        }
    }

    private static BigInteger calculateOperations(List<BigInteger> numbers, String[] operations) {
        BigInteger result = numbers.getFirst();
        for (int i = 0; i < operations.length; i++) {
            String operation = operations[i];
            BigInteger nextNumber = numbers.get(i + 1);

            switch (operation) {
                case "+" -> result = result.add(nextNumber);
                case "*" -> result = result.multiply(nextNumber);
                case "||" -> {
                    String concatenated = result.toString() + nextNumber.toString();
                    result = new BigInteger(concatenated);
                }
            }
        }
        return result;
    }
}
