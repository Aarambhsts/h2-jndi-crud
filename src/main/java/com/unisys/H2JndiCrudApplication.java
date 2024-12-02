package com.unisys;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class H2JndiCrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(H2JndiCrudApplication.class, args);
	}

}
