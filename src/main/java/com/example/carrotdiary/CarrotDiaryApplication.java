package com.example.carrotdiary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CarrotDiaryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarrotDiaryApplication.class, args);
	}

}
