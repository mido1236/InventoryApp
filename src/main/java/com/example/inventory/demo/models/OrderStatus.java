package com.example.inventory.demo.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;

public enum OrderStatus {
  REGULAR(1),
  CANCELLATION(2);
  static HashMap<Integer, OrderStatus> valueMap;

  static {
    valueMap = new HashMap<Integer, OrderStatus>();
    for (OrderStatus status : OrderStatus.values()) {
      valueMap.put(status.getValue(), status);
    }
  }

  public static OrderStatus getStatus(int value) {
    return valueMap.get(value);
  }

  public int getValue() {
    return value;
  }

  private int value;

  OrderStatus(int value) {
    this.value = value;
  }

  @JsonCreator
  public static OrderStatus create(Integer status) {
    return OrderStatus.getStatus(status);
  }
}
