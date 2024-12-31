package com.learn.microservices.inventory_service.repositories;

import com.learn.microservices.inventory_service.Entities.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
    Optional<Inventory> findByProductCode(String productCode);
}

