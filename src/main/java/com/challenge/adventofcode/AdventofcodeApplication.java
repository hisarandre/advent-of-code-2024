package com.challenge.adventofcode;

import com.challenge.adventofcode.day01.Day01;
import com.challenge.adventofcode.day02.Day02;
import com.challenge.adventofcode.day03.Day03;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class AdventofcodeApplication {


	public static void main(String[] args) throws IOException {
		SpringApplication.run(AdventofcodeApplication.class, args);
		Day03.solve();
	}
}
