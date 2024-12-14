package com.challenge.adventofcode;

import com.challenge.adventofcode.twentyFour.*;
import com.challenge.adventofcode.twentyFour.day06.Day06;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AdventofcodeApplication {
	
	public static void main(String[] args) throws Exception {
		Day12 day12 = new Day12();
		day12.solve();
	}
}
