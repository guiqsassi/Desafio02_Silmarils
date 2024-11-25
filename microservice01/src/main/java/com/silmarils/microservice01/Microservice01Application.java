package com.silmarils.microservice01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class Microservice01Application {

	public static void main(String[] args) {
		SpringApplication.run(Microservice01Application.class, args);
	}

}
