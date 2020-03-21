package com.chg.hackdays.chappie.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ChappieAdapterDmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChappieAdapterDmsApplication.class, args);
	}

	@Bean
	public RestTemplate template() {
		return new RestTemplate();
	}

}
