package com.yusufguc.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = {"com.yusufguc"})
@EnableJpaRepositories(basePackages = {"com.yusufguc"})
@ComponentScan(basePackages = {"com.yusufguc"})
public class FoodOrderingApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderingApiApplication.class, args);
	}

}
