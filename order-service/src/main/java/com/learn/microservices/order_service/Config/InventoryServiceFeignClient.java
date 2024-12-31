package com.learn.microservices.order_service.Config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "inventory-service")
public interface InventoryServiceFeignClient {
    @GetMapping("/inventory/check/{productCode}")
    int checkInventory(@PathVariable String productCode);

    @PostMapping("/inventory/deduct/{productCode}/{quantity}")
    ResponseEntity<String> deductStock(@PathVariable String productCode, @PathVariable int quantity);
}

