package com.challenge.adventofcode;

import com.challenge.adventofcode.day03.Day03;
import com.challenge.adventofcode.day04.Day04;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AdventofcodeApplication {
	
	public static void main(String[] args) throws IOException {
		Day04.solve();
	}
}
