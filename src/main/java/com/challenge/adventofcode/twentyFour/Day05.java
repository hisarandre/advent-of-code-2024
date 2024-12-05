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
        List<String> validLines = new ArrayList<>();

        Map<Integer, Set<Integer>> rulesMap = convertRules(rules);

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

                    if(!isCorrect){
                        break;
                    }
                }
            }

            if (isCorrect){
                validLines.add(line);
            }
        }

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

//    public static void bubbleSort(List<Integer> pageNumbers){
//        boolean swapped;
//
//        for (int pageNb = 0; pageNb < pageNumbers.size() - 1; pageNb++) {
//            swapped = false;
//            for (int i = 0; i < pageNumbers.size() - pageNb - 1; pageNb++) {
//                if (pageNumbers.get(i) > pageNumbers.get(i +1)) {
//                    int temp = pageNumbers.get(i);
//                    pageNumbers.set(i, pageNumbers.get(i + 1));
//                    pageNumbers.set(i + 1, temp);
//                    swapped = true;
//                }
//            }
//
//            if (!swapped) break;
//        }
//
//        return swapped;
//    }
}
