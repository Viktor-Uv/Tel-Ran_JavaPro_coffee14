package com.example.coffee14;

// https://start.spring.io/ - spring project online generator

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling // включает возможность периодического выполнения задач в приложении Spring
public class Coffee14Application {

	public static void main(String[] args) {
		SpringApplication.run(Coffee14Application.class, args);
	}

}
