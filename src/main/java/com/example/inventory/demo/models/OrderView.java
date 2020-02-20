package com.example.inventory.demo.models;

import java.util.ArrayList;

public class OrderView extends Order {

  ArrayList<Integer> ids;
  ArrayList<Integer> quantities;

  public ArrayList<Integer> getIds() {
    return ids;
  }

  public void setIds(ArrayList<Integer> ids) {
    this.ids = ids;
  }

  public ArrayList<Integer> getQuantities() {
    return quantities;
  }

  public void setQuantities(ArrayList<Integer> quantities) {
    this.quantities = quantities;
  }
}
