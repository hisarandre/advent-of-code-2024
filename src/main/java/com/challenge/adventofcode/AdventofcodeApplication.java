package com.challenge.adventofcode;

import com.challenge.adventofcode.twentyFour.day12.Day12;
import com.challenge.adventofcode.twentyFour.day13.Day13;
import com.challenge.adventofcode.twentyFour.day14.Day14;
import com.challenge.adventofcode.twentyFour.day15.Day15;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdventofcodeApplication {
	
	public static void main(String[] args) throws Exception {
		Day12 day12 = new Day12();
		Day13 day13 = new Day13();
		Day14 day14 = new Day14();
		Day15 day15 = new Day15();

		day15.solve();
	}
}
