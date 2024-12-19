package com.challenge.adventofcode;

import com.challenge.adventofcode.twentyFour.day12.Day12;
import com.challenge.adventofcode.twentyFour.day13.Day13;
import com.challenge.adventofcode.twentyFour.day14.Day14;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AdventofcodeApplication {
	
	public static void main(String[] args) throws Exception {
		Day14 day14 = new Day14();
		day14.solve();
	}
}
