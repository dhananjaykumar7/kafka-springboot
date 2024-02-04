package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class AppConfig {
	
	@Bean
	public ObjectMapper mapper() {
		return new ObjectMapper();
	}

}
