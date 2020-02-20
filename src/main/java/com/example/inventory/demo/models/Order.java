package com.example.inventory.demo.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`Order`")
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  Integer id;

  Date date;
  OrderStatus status;
  String customerEmail;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "order", orphanRemoval = true)
  private List<OrderItem> orderItems = new ArrayList<>();

  public Integer getId() {
    return id;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public OrderStatus getStatus() {
    return status;
  }

  public void setStatus(OrderStatus status) {
    this.status = status;
  }

  public String getCustomerEmail() {
    return customerEmail;
  }

  public void setCustomerEmail(String customerEmail) {
    this.customerEmail = customerEmail;
  }
}
