package com.challenge.adventofcode.day03;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03 {

    public static void solve() throws IOException {
        String inputFile = "input03.txt";
        String fileContent = InputHelper.readInput(inputFile);
        int sum = code(fileContent, false);
        System.out.println("The solution is: " + sum);
    }

    public static int code(String fileContent, Boolean isPartOne) {
        String[] lines = fileContent.split("\n");
        String singleLine = fileContent.replaceAll("\n", " ");

        if (!isPartOne) {
            singleLine = cleanLine(singleLine);
        }

        return calculateMul(singleLine);
    }

    public static int calculateMul(String line) {
        int sum = 0;

        String mulRegex = "mul\\(\\d+,\\d+\\)"; // look for mul(00,00)
        Pattern mulPattern = Pattern.compile(mulRegex);
        Matcher mulMatcher = mulPattern.matcher(line);

        List<String> matches = new ArrayList<>();

        while (mulMatcher.find()) {
            matches.add(mulMatcher.group());
        }

        String firstNbRegex = "\\d+,"; // look for  00,
        String secondNbRegex = "\\d+\\)"; // look for OO)
        Pattern firstPattern = Pattern.compile(firstNbRegex);
        Pattern secPattern = Pattern.compile(secondNbRegex);

        for (String match : matches) {
            Matcher firstMatcher = firstPattern.matcher(match);
            Matcher secondMatcher = secPattern.matcher(match);

            List<Integer> firstNbs = new ArrayList<>();
            List<Integer> secNbs= new ArrayList<>();

            if (firstMatcher.find()){
                String firstNb = firstMatcher.group();
                firstNb = firstNb.substring(0, firstNb.length() - 1);
                firstNbs.add(Integer.parseInt(firstNb));
            }

            if (secondMatcher.find()){
                String secNb = secondMatcher.group();
                secNb = secNb.substring(0, secNb.length() - 1);
                secNbs.add(Integer.parseInt(secNb));
            }

            for (int i = 0; i < firstNbs.size(); i++) {
                int mul = firstNbs.get(i) * secNbs.get(i);
                sum += mul;
            }
        }
        return sum;
    }

    public static String cleanLine(String singleLine){
        List<String> doLines = new ArrayList<>();

        String doDontRegex = "don't\\(.*?\\)|do\\(.*?\\)"; // look for do() or don't()
        Pattern doDontPattern = Pattern.compile(doDontRegex);
        Matcher doDontMatcher = doDontPattern.matcher(singleLine);

        StringBuilder cleanedLine = new StringBuilder();
        boolean isEnabled = true;
        int prevIndex = 0;

        while (doDontMatcher.find()) {
            String beforeDoDont = singleLine.substring(prevIndex, doDontMatcher.start());

            if (isEnabled) {
                cleanedLine.append(beforeDoDont);
            }

            //System.out.println("last match: " + doDontMatcher.group());
            isEnabled = !doDontMatcher.group().contains("don't");
            //System.out.println("isEnable: " + isEnabled);
            prevIndex = doDontMatcher.end();
        }

        String endLine = singleLine.substring(prevIndex);

        if (isEnabled) {
            cleanedLine.append(endLine);
        }

        return cleanedLine.toString();
    }
}
