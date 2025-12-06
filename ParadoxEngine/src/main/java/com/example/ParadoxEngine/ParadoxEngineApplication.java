package com.example.ParadoxEngine;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ParadoxEngineApplication {

	public static void main(String[] args) {
		SpringApplication.run(ParadoxEngineApplication.class, args);
	}

}
