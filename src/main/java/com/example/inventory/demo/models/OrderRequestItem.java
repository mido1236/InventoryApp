package com.example.inventory.demo.models;

public class OrderRequestItem {
  public int getInventoryId() {
    return inventoryId;
  }

  public void setInventoryId(int inventoryId) {
    this.inventoryId = inventoryId;
  }

  int inventoryId;
  int quantity;

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }
}
