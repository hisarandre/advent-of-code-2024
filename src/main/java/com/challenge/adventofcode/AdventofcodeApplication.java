package com.challenge.adventofcode;

import com.challenge.adventofcode.twentyFour.day12.Day12;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdventofcodeApplication {
	
	public static void main(String[] args) throws Exception {
		Day12 day12 = new Day12();
		day12.solve();
	}
}
