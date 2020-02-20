package com.example.inventory.demo.repository;

import com.example.inventory.demo.models.OrderItem;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
  List<OrderItem> deleteOrderItemsByOrder_IdIs(int orderId);
}
