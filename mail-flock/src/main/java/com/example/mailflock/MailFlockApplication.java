package com.example.mailflock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync(proxyTargetClass = true)
public class MailFlockApplication {

	public static void main(String[] args) {
		SpringApplication.run(MailFlockApplication.class, args);
	}

}
