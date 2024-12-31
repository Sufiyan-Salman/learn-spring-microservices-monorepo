package com.learn.microservices.inventory_service.controllers;

import com.learn.microservices.inventory_service.Entities.Inventory;
import com.learn.microservices.inventory_service.repositories.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryRepository inventoryRepository;

    @PostMapping
    public Inventory createInventory(@RequestBody Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @GetMapping("/{id}")
    public Inventory getInventory(@PathVariable Long id) {
        return inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Inventory not found"));
    }
    @GetMapping("/check/{productCode}")
    public int checkInventory(@PathVariable String productCode) {
        Optional<Inventory> inventory = inventoryRepository.findByProductCode(productCode);
        return inventory.isPresent() ? inventory.get().getQuantity() : 0;


    }

    @PostMapping("/deduct/{productCode}/{quantity}")
    public ResponseEntity<String> deductStock(@PathVariable String productCode, @PathVariable int quantity) {
        Inventory inventory = (Inventory) inventoryRepository.findByProductCode(productCode)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        if (inventory.getQuantity() >= quantity) {
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);
            return ResponseEntity.ok("Stock deducted.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Insufficient stock.");
        }
    }
}


