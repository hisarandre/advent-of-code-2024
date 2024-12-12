package com.challenge.adventofcode;

import com.challenge.adventofcode.twentyFour.*;
import com.challenge.adventofcode.twentyFour.day06.Day06;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AdventofcodeApplication {
	
	public static void main(String[] args) throws IOException {
		Day11 day11 = new Day11();
		day11.solve();
	}
}
