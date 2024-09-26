package com.edra.logClient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LogClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogClientApplication.class, args);
	}

}
