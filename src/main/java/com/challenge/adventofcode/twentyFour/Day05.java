package com.challenge.adventofcode.twentyFour;

import com.challenge.adventofcode.helper.InputHelper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Day05 extends Solver{

    public Day05() {
        super("input05.txt",false );
    }


    public int code(String fileContent, Boolean isPartOne) throws IOException {
        int sum = 0;
        String rulesContent = InputHelper.readInput("rule05.txt");
        String[] lines = fileContent.split("\n");
        String[] rules = rulesContent.split("\n");
        Map<Integer, Set<Integer>> rulesMap = convertRules(rules);

        List<String> validLines = checkLines(rulesMap, lines, isPartOne);
        List<String> orderedLines = new ArrayList<>();

        if (!isPartOne){
            for (String line : validLines) {
                List<Integer> pageNumbers = Arrays.stream(line.split(","))
                        .map(Integer::parseInt)
                        .toList();

                String orderedLine = sort(pageNumbers, rulesMap);
                orderedLines.add(orderedLine);
            }

            sum = calculateMiddleNb(orderedLines);
        } else {
            sum = calculateMiddleNb(validLines);
        }

        return sum;
    }

    public static List<String> checkLines(Map<Integer, Set<Integer>> rulesMap, String[] lines, boolean isPartOne){
        List<String> validLines = new ArrayList<>();

        for (String line : lines){
            boolean isCorrect = true;

            List<Integer> pageNumbers = Arrays.stream(line.split(","))
                    .map(Integer::parseInt)
                    .toList();

            for (int i = 0; i < pageNumbers.size(); i++ ){
                int nbToCheck = pageNumbers.get(i);

                if (rulesMap.containsKey(nbToCheck)) {
                    Set<Integer> mustBeBefore = rulesMap.get(nbToCheck);
                    isCorrect = checkCorrectOrder(pageNumbers, nbToCheck, mustBeBefore);

                    if (!isCorrect){
                        if(!isPartOne){
                            validLines.add(line);
                        }
                        break;
                    }
                }
            }

            if (isCorrect && isPartOne){
                validLines.add(line);
            }
        }
        return validLines;
    }

    public static int calculateMiddleNb(List<String> validLines){
        int sum = 0;
        for (String line : validLines){
            List<Integer> pageNumbers = Arrays.stream(line.split(","))
                    .map(Integer::parseInt)
                    .toList();

            int middleIndex = pageNumbers.size() / 2;
            int middleNb =  pageNumbers.get(middleIndex);
            sum = sum + middleNb;
        }
        return sum;
    }

    public static boolean checkCorrectOrder(List<Integer> pageNumbers, Integer nbToCheck, Set<Integer> mustBeBeforeNbrs){
        boolean isValid = true;
        int NbToCheckIndex = pageNumbers.indexOf(nbToCheck);

        for (Integer pageNb : pageNumbers){
            if (mustBeBeforeNbrs.contains(pageNb)){
                int mustBeBeforeIndex = pageNumbers.indexOf(pageNb);
                if (mustBeBeforeIndex < NbToCheckIndex){
                    return false;
                }
            }
        }
        return isValid;
    }

    public static Map<Integer, Set<Integer>> convertRules( String[] rules){

        Map<Integer, Set<Integer>> rulesMap = new HashMap<>();

        for (String rule : rules){
            int firstNb = Integer.parseInt(rule.substring(0, 2));
            int secNb = Integer.parseInt(rule.substring(rule.indexOf('|') + 1));
            rulesMap.computeIfAbsent(firstNb, k -> new HashSet<>()).add(secNb);
        }

        return rulesMap;
    }

    public static String sort(List<Integer> pageNumbers, Map<Integer, Set<Integer>> rulesMap) {
        List<Integer> numbers = new ArrayList<>(pageNumbers);

        for (int i = 0; i < numbers.size(); i++) {
            int current = numbers.get(i);

            if (rulesMap.containsKey(current)) {
                Set<Integer> mustBeBefore = rulesMap.get(current);

                for (int j = 0; j < i; j++) {
                    int previous = numbers.get(j);

                    if (mustBeBefore.contains(previous)) {
                        numbers.set(j, current);
                        numbers.set(i, previous);
                        i = -1;
                        break;
                    }
                }
            }
        }

        return numbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

}
