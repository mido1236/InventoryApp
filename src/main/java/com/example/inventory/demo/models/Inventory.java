package com.example.inventory.demo.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Inventory {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;

  double price;
  String Description;
  String Name;
  int quantity;

  private Inventory(Builder builder) {
    setPrice(builder.price);
    setDescription(builder.Description);
    setName(builder.Name);
    setQuantity(builder.quantity);
  }

  public Inventory() {}

  public Integer getId() {
    return id;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDescription() {
    return Description;
  }

  public void setDescription(String description) {
    Description = description;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public static final class Builder {
    private double price;
    private String Description;
    private String Name;
    private int quantity;

    public Builder() {}

    public Builder price(double val) {
      price = val;
      return this;
    }

    public Builder Description(String val) {
      Description = val;
      return this;
    }

    public Builder Name(String val) {
      Name = val;
      return this;
    }

    public Builder quantity(int val) {
      quantity = val;
      return this;
    }

    public Inventory build() {
      return new Inventory(this);
    }
  }
}
