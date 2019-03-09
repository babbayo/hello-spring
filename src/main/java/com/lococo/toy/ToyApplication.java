package com.lococo.toy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@SpringBootApplication
@EnableMongoAuditing
public class ToyApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToyApplication.class, args);
	}

}
