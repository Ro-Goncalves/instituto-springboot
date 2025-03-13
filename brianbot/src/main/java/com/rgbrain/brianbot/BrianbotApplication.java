package com.rgbrain.brianbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@SpringBootApplication
@EnableRetry
public class BrianbotApplication {

	public static void main(String[] args) {
		SpringApplication.run(BrianbotApplication.class, args);
	}
}
