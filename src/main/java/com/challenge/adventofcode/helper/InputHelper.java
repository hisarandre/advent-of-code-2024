package com.challenge.adventofcode.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class InputHelper {

    public static String readInput(String filename) throws IOException {
        return Files.readString(Paths.get("src/main/resources/inputs/" + filename));
    }
}
