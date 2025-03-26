package com.orderService;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
@EntityScan(basePackages = {"com.notificationService.entity"})
public class OrderServiceApplication {

	public static void main(String[] args) {
		System.out.println("Inside order");
		SpringApplication.run(OrderServiceApplication.class, args);
	}




}
