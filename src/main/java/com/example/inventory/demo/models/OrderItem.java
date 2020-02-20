package com.example.inventory.demo.models;

import javax.persistence.*;

@Entity
public class OrderItem {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;

  @ManyToOne
  @JoinColumn(name = "fk_inventoryid")
  Inventory inventory;

  @ManyToOne
  @JoinColumn(name = "fk_orderid")
  Order order;

  int quantity;

  public OrderItem() {}

  public OrderItem(Inventory inventory, Order order, Integer quantity) {
    this.inventory = inventory;
    this.order = order;
    this.quantity = quantity;
  }
}
