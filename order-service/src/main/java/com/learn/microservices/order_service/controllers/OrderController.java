package com.learn.microservices.order_service.controllers;

import com.learn.microservices.order_service.Config.InventoryServiceFeignClient;
import com.learn.microservices.order_service.Entities.Order;
import com.learn.microservices.order_service.repositories.OrderRepository;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
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
//    TODO: Take the resiliience logic to a central class to avoid code duplicate
    @PostMapping("/place-order")
    @CircuitBreaker(name = "inventory-service")
    @Retry(name = "inventory-service", fallbackMethod = "orderFallback")
    @RateLimiter(name = "inventory-service")
    @Bulkhead(name = "inventory-service")
    public ResponseEntity<String> placeOrder(@RequestBody Order order) {
        System.out.println("in place order");
        int stock = inventoryServiceFeignClient.checkInventory(order.getProductCode());
        System.out.println("recieved stock: "+stock);
        if (stock >= order.getQuantity()) {
            System.out.println("placing order ");
            inventoryServiceFeignClient.deductStock(order.getProductCode(), order.getQuantity());
//            orderRepository.save(order);
            System.out.println("placed order");
            return ResponseEntity.ok("Order placed successfully.");
        } else {
            System.out.println("out of stock");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product out of stock.");
        }
    }
    public ResponseEntity<String> orderFallback(Order order, Throwable t) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body("Inventory Service is currently unavailable. Please try again later.");
    }

    @GetMapping("/{userId}")
    public List<Order> getOrdersByUserId(@PathVariable Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @GetMapping("/retry-sample")
    @Retry(name = "retry-sample", fallbackMethod = "testRetryCorrectMethod") // different config for different services, add this in config file
    public String testRetryViaResilience4j(){
        System.out.println("======Retry Method Called======");
        int a = 5/0;
        return "hi";
    }
    @GetMapping("/circuit-sample")
//    @Retry(name = "retry-sample", fallbackMethod = "testRetryCorrectMethod") // different config for different services, add this in config file
//    @CircuitBreaker(name = "default") // different config for different services, add this in config file
    @RateLimiter(name = "inventory-service")
    @Bulkhead(name = "inventory-service")
    public String testCircuitViaResilience4j(){
        System.out.println("======Circuit breakr Method Called======");
//        int a = 5/0;
        return "hi";
    }
    public String testRetryCorrectMethod(Exception ex){ // fallabck method must accept throwable argument or else it will not work, we can also have different fallbacks for different exceptions
        System.out.println("Ex is: "+ex.getMessage());
        return "Response from fallback method";
    }
    @GetMapping("/instance")
    public String getInstance() {
        return "Order Service is running on port ";
    }
}


