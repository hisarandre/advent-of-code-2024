package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.util.*;
import java.util.concurrent.*;

public class Day11 {
    record Key(int t, long n) {}
    Map<Key, Long> CACHE = new HashMap<>();


    public void solve() throws Exception {
        String fileContent = InputHelper.readInput("input11.txt");

        long[] stones = Arrays.stream(fileContent.split(" "))
                .mapToLong(Long::parseLong)
                .toArray();

        long sum = code(stones, false);

        System.out.println("The solution is: " + sum);
    }

    public long code(long[] stones, boolean isPartOne) throws Exception {
        int blinks = 75;
        long sum = 0;

        for (long stone : stones) {
            sum += rearrangeStones(0, stone,blinks);
        }

        return sum;
    }

    private long rearrangeStones(int index, long stone, long blinks) {
        if (index >= blinks) {
            return 1L;
        }

        Key key = new Key(index, stone);

        if (CACHE.containsKey(key)) {
            return CACHE.get(key);
        }

        long value;
        long absStone = Math.abs(stone);
        String nbStr = Long.toString(absStone);
        int length = nbStr.length();

        if (stone == 0) {
            value = rearrangeStones(index + 1, 1L, blinks);
        } else if (length % 2 == 0) {
                String leftHalf = nbStr.substring(0, length / 2);
                String rightHalf = nbStr.substring(length / 2);
                long leftStone = Long.parseLong(leftHalf);
                long rightStone = Long.parseLong(rightHalf);
            value = rearrangeStones(index + 1, leftStone , blinks) + rearrangeStones(index + 1, rightStone, blinks);
        } else {
            value = rearrangeStones(index + 1, stone * 2024, blinks);
        }

        CACHE.put(key, value);
        return value;
    }

}
