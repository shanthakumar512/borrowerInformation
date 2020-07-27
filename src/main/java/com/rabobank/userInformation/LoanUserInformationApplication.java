package com.rabobank.userinformation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
class LoanUserInformationApplication {

	public static void main(String[] args) {
		SpringApplication.run(LoanUserInformationApplication.class, args);
	}

}
