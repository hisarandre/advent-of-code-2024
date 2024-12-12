package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

public class Day09{

    public void solve() throws IOException {
        String fileContent = InputHelper.readInput("input09.txt");
        BigInteger sum = code(fileContent, false);
        System.out.println("The solution is: " + sum);
    }


    public BigInteger code(String fileContent, Boolean isPartOne) throws IOException {
        String[] firstLine = generateFirstLine(fileContent);

        String[] nextLine = firstLine;
        String[] lastLineGenerated = firstLine;

        if(!isPartOne){
            lastLineGenerated = generateNextLinePartTwo(nextLine);
        }

        if(isPartOne){
            while(true){
                nextLine = generateNextLinePartOne(nextLine);
                if (nextLine == null) break;
                lastLineGenerated = nextLine;
            }
        }

        return calculateResult(lastLineGenerated);
    }


    public static BigInteger calculateResult(String[] lastLine) {
        BigInteger sum = BigInteger.ZERO;

        for (int i = 0; i < lastLine.length; i++) {
            if (lastLine[i].equals(".")) {
                continue;
            }
            BigInteger position = BigInteger.valueOf(i);
            BigInteger value = BigInteger.valueOf(Integer.parseInt(lastLine[i]));
            sum = sum.add(position.multiply(value));
        }

        return sum;
    }

    public static String[] generateNextLinePartTwo(String[] line) {
        int fileCount = countFiles(line);

        // read files from highest to lowest
        for (int fileId = fileCount ; fileId >= 0; fileId--) {
            // Find the current location of the file
            int[] fileInfo = findFile(line, fileId);
            int fileLength = fileInfo[1];
            int fileStart = fileInfo[0];

            // If file not found, continue to next file
            if (fileStart == -1) continue;

            // Find a free space to the left
            int freeSpaceStart = findFreeSpaceToLeft(line, fileStart, fileLength);

            // Move the file if free space found
            if (freeSpaceStart != -1) {
                String[] fileCopy = Arrays.copyOfRange(line, fileStart, fileStart + fileLength);
                Arrays.fill(line, fileStart, fileStart + fileLength, ".");
                System.arraycopy(fileCopy, 0, line, freeSpaceStart, fileLength);
            }
        }

        return line;
    }


    private static int findFreeSpaceToLeft(String[] line, int currentStart, int fileLength) {
        for (int i = 0; i < currentStart; i++) {
            boolean canFit = true;
            for (int j = 0; j < fileLength; j++) {
                if (i + j >= line.length || !line[i + j].equals(".")) {
                    canFit = false;
                    break;
                }
            }
            if (canFit) {
                return i;
            }
        }
        return -1;
    }

    private static int countFiles(String[] blocks) {
        int count = 0;
        String previousBlock = ".";

        for (String block : blocks) {
            if (!block.equals(".") && !block.equals(previousBlock)) {
                count++;
            }
            previousBlock = block;
        }
        return count;
    }

    private static int[] findFile(String[] blocks, int fileId) {
        int currentFileId = 0;

        for (int i = 0; i < blocks.length; i++) {
            if (!blocks[i].equals(".")) {
                int blockFileId = Integer.parseInt(blocks[i]);

                if (blockFileId == fileId) {
                    int fileLength = 1;
                    for (int j = i + 1; j < blocks.length && blocks[j].equals(blocks[i]); j++) {
                        fileLength++;
                    }
                    return new int[]{i, fileLength};
                }

                while (i < blocks.length && !blocks[i].equals(".") && blocks[i].equals(String.valueOf(blockFileId))) {
                    i++;
                }
                i--;
            }
        }

        return new int[]{-1, 0};
    }


    public static String[] generateNextLinePartOne(String[] line) {
        String[] nextLine = Arrays.copyOf(line, line.length);

        String lastDigit = null;
        int lastDigitIndex = -1;
        int dotIndex = -1;

        for (int i = 0; i < line.length; i++) {
            if (!line[i].equals(".")) {
                lastDigit = line[i];
                lastDigitIndex = i;
            }

            if (line[i].equals(".") && dotIndex == -1) {
                dotIndex = i;
            }
        }

        if (dotIndex != -1 && lastDigitIndex != -1 && dotIndex < lastDigitIndex) {
            nextLine[dotIndex] = lastDigit;
            nextLine[lastDigitIndex] = ".";
            return nextLine;
        } else {
            return null;
        }
    }

    public static String[] generateFirstLine(String line) {
        List<String> blocks = new ArrayList<>();
        boolean isFreeSpace = false;
        int code = 0;

        for (char nb : line.toCharArray()) {
            int count = Character.getNumericValue(nb);

            if (isFreeSpace) {
                for (int i = 0; i < count; i++) {
                    blocks.add(".");
                }
                isFreeSpace = false;
            } else {
                for (int i = 0; i < count; i++) {
                    blocks.add(String.valueOf(code));
                }
                code++;
                isFreeSpace = true;
            }
        }
        return blocks.toArray(new String[0]);
    }
}


