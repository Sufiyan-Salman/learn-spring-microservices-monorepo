package com.learn.microservices.order_service.controllers;

import com.learn.microservices.order_service.Config.InventoryServiceFeignClient;
import com.learn.microservices.order_service.Entities.Order;
import com.learn.microservices.order_service.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InventoryServiceFeignClient inventoryServiceFeignClient;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        int stock = inventoryServiceFeignClient.checkInventory(order.getProductCode());
        if (stock >= order.getQuantity()) {
            inventoryServiceFeignClient.deductStock(order.getProductCode(), order.getQuantity());
            orderRepository.save(order);
            return ResponseEntity.ok("Order placed successfully.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product out of stock.");
        }
    }

    @GetMapping("/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }
}


