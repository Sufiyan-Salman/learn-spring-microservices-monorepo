package com.learn.microservices.inventory_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
//		exclude = {
//				org.redisson.spring.starter.RedissonAutoConfigurationV2.class
//		}
)
@EnableFeignClients
@EnableCaching

public class InventoryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(InventoryServiceApplication.class, args);
	}

}
