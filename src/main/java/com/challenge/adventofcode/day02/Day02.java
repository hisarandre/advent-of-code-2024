package com.challenge.adventofcode.day02;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day02 {

    public static void solve() throws IOException {
        String inputFile = "input02.txt";
        String fileContent = InputHelper.readInput(inputFile);
        int sum = code(fileContent, true);
        System.out.println("The solution is: " + sum);
    }

    public static int code(String fileContent, Boolean isPartOne) {

        int sum = 0;
        String[] lines = fileContent.split("\n");

        for (String line : lines) {

            String[] numbers = line.trim().split("\\s+");

            int[] intArray = Arrays.stream(numbers)
                    .mapToInt(Integer::parseInt)
                    .toArray();

            Boolean isValid = false;

            for (int i = 0; i < intArray.length; i++) {
                isValid = isValid(intArray);
            }

            if (!isPartOne && !isValid){
                for (int i = 0; i < intArray.length; i++) {
                    int[] modifiedArray = removeElementAtIndex(intArray, i);
                    isValid = isValid(modifiedArray);
                    if (isValid) {
                        break;
                    }
                }
            }

            if (isValid) {
                sum++;
            }
        }
        return sum;
    }

    public static Boolean isValid(int[] intArray) {
        boolean isValid = true;

        boolean isIncreasing = intArray[0] < intArray[1];

        for (int i = 0; i < intArray.length - 1; i++) {
            int difference = Math.abs(intArray[i] - intArray[i + 1]);

            if (difference < 1 || difference > 3) {
                isValid = false;
                break;
            }

            if (isIncreasing && intArray[i] >= intArray[i + 1]) {
                isValid = false;
                break;
            }
            if (!isIncreasing && intArray[i] <= intArray[i + 1]) {
                isValid = false;
                break;
            }
        }
        return isValid;
    }

    public static int[] removeElementAtIndex(int[] array, int index) {
        int[] newArray = new int[array.length - 1];
        System.arraycopy(array, 0, newArray, 0, index);
        System.arraycopy(array, index + 1, newArray, index, array.length - index - 1);
        return newArray;
    }
}
