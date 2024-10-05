package com.thecodealchemist.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"com.thecodealchemist"})
@EnableJpaRepositories(basePackages = {"com.thecodealchemist.repository"})
@EntityScan(basePackages = {"com.thecodealchemist.entity"})
public class TaskApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskApiApplication.class, args);
	}

}
