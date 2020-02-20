package com.example.inventory.demo.repository;

import com.example.inventory.demo.models.Inventory;
import com.example.inventory.demo.models.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<Order, Integer> {
}
