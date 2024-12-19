package com.challenge.adventofcode.twentyFour.day13;

import com.challenge.adventofcode.helper.InputHelper;
import java.io.IOException;
import java.math.BigInteger;
import java.util.*;

public class Day13 {
    private static final BigInteger COST_A = BigInteger.valueOf(3);
    private static final BigInteger COST_B = BigInteger.valueOf(1);
    private static final BigInteger LARGE_OFFSET = BigInteger.valueOf(10_000_000_000_000L);

    public void solve() throws IOException {
        String fileContent = InputHelper.readInput("input13.txt");
        BigInteger sumPartOne = solve(fileContent, BigInteger.ZERO, true);
        System.out.println("Part One solution: " + sumPartOne);
        BigInteger sumPartTwo = solve(fileContent, LARGE_OFFSET, false);
        System.out.println("Part Two solution: " + sumPartTwo);
    }

    private BigInteger solve(String fileContent, BigInteger offset, boolean isPartOne) {
        return convertToClawMachines(fileContent, offset, isPartOne)
                .stream()
                .map(this::calculateMachineCost)
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

    private BigInteger calculateMachineCost(Machine machine) {
        BigInteger prizeX = machine.getPrize().getPositionX();
        BigInteger prizeY = machine.getPrize().getPositionY();

        BigInteger ax = machine.getButtonA().getPositionX();
        BigInteger ay = machine.getButtonA().getPositionY();
        BigInteger bx = machine.getButtonB().getPositionX();
        BigInteger by = machine.getButtonB().getPositionY();

        BigInteger numerator = prizeX.multiply(ay).subtract(prizeY.multiply(ax));
        BigInteger denominator = bx.multiply(ay).subtract(by.multiply(ax));

        if (denominator.equals(BigInteger.ZERO)) {
            return BigInteger.ZERO;
        }

        BigInteger b = numerator.divide(denominator);
        BigInteger remX = prizeX.subtract(b.multiply(bx));

        BigInteger l = ax.equals(BigInteger.ZERO) ? prizeY : remX;
        BigInteger r = ax.equals(BigInteger.ZERO) ? ay : ax;
        BigInteger a = l.divide(r);

        if (a.multiply(ay).add(b.multiply(by)).equals(prizeY) && l.mod(r).equals(BigInteger.ZERO)) {
            return a.multiply(COST_A).add(b.multiply(COST_B));
        }

        return BigInteger.ZERO;
    }

    public static List<Machine> convertToClawMachines(String fileContent, BigInteger offset, boolean isPartOne) {
        String[] lines = fileContent.split("\n");
        List<Machine> machines = new ArrayList<>();
        Position buttonA = null, buttonB = null, prize = null;

        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("Button A:")) {
                buttonA = parsePosition(line);
            } else if (line.startsWith("Button B:")) {
                buttonB = parsePosition(line);
            } else if (line.startsWith("Prize:")) {
                prize = parsePosition(line.replace("=", "+"));

                // Modify prize position for part two
                if (!isPartOne) {
                    prize = new Position(
                            prize.getPositionX().add(offset),
                            prize.getPositionY().add(offset)
                    );
                }

                if (buttonA != null && buttonB != null && prize != null) {
                    machines.add(new Machine(buttonA, buttonB, prize));
                    buttonA = null;
                    buttonB = null;
                    prize = null;
                }
            }
        }
        return machines;
    }

    private static Position parsePosition(String line) {
        String[] parts = line.split(":")[1].trim().split(",");
        BigInteger x = new BigInteger(parts[0].split("\\+")[1].trim());
        BigInteger y = new BigInteger(parts[1].split("\\+")[1].trim());
        return new Position(x, y);
    }
}