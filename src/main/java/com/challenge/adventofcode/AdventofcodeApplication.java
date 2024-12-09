package com.challenge.adventofcode;

import com.challenge.adventofcode.twentyFour.*;
import com.challenge.adventofcode.twentyFour.day06.Day06;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AdventofcodeApplication {
	
	public static void main(String[] args) throws IOException {
		Day04 day04 = new Day04();
		Day05 day05 = new Day05();
		Day06 day06 = new Day06();
		Day07 day07 = new Day07();
		Day08 day08 = new Day08();
		Day09 day09 = new Day09();

		day09.solve();
	}
}
