package com.example.inventory.demo.repository;

import com.example.inventory.demo.models.Inventory;
import org.springframework.data.repository.CrudRepository;

public interface InventoryRepository extends CrudRepository<Inventory, Integer> {
}
