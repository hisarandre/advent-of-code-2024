package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class Day11 {

    public void solve() throws IOException {
        String fileContent = InputHelper.readInput("input11.txt");

        long[] stones = Arrays.stream(fileContent.split(" "))
                .mapToLong(Long::parseLong)
                .toArray();

        long sum = code(stones, false);

        System.out.println("The solution is: " + sum);
    }

    public long code(long[] stones, boolean isPartOne) throws IOException {
        int blinks = 75 ;

        for (int i = 0; i < blinks; i++) {
            stones = rearrangementOfStones(stones);
            System.out.println("Iteration " + (i + 1) + " completed.");
        }

        return stones.length;
    }

    public static long[] rearrangementOfStones(long[] stones) {
        if (stones == null) return new long[0];

        long[] result = new long[stones.length * 2];
        int resultIndex = 0;

        for (long current : stones) {
            if (current == 0L) {
                result[resultIndex++] = 1L;
            } else {
                boolean isNegative = current < 0;
                long absStone = Math.abs(current);
                String nbStr = Long.toString(absStone);
                int length = nbStr.length();

                if (length % 2 == 0) {
                    String leftHalf = nbStr.substring(0, length / 2);
                    String rightHalf = nbStr.substring(length / 2);
                    long leftStone = Long.parseLong(leftHalf);
                    long rightStone = Long.parseLong(rightHalf);

                    if (isNegative) {
                        leftStone = -leftStone;
                        rightStone = -rightStone;
                    }

                    result[resultIndex++] = leftStone;
                    result[resultIndex++] = rightStone;
                } else {
                    result[resultIndex++] = current * 2024;
                }
            }
        }
        return Arrays.copyOf(result, resultIndex);
    }
}

