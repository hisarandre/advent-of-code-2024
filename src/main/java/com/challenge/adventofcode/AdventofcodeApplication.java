package com.challenge.adventofcode;

import com.challenge.adventofcode.twentyFour.Day04;
import com.challenge.adventofcode.twentyFour.Day05;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AdventofcodeApplication {
	
	public static void main(String[] args) throws IOException {
		Day04 day04 = new Day04();
		Day05 day05 = new Day05();

		day05.solve();
	}
}
