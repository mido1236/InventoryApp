package com.example.inventory.demo;

import com.example.inventory.demo.models.Inventory;
import com.example.inventory.demo.models.Order;
import com.example.inventory.demo.models.OrderItem;
import com.example.inventory.demo.models.OrderView;
import com.example.inventory.demo.repository.InventoryRepository;
import com.example.inventory.demo.repository.OrderItemRepository;
import com.example.inventory.demo.repository.OrderRepository;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;

@RestController
public class OrderController {

  public static final String ORDERS = "orders";
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;
  private final InventoryRepository inventoryRepository;

  public OrderController(
      OrderRepository orderRepository,
      OrderItemRepository orderItemRepository,
      InventoryRepository inventoryRepository) {
    this.orderRepository = orderRepository;
    this.orderItemRepository = orderItemRepository;
    this.inventoryRepository = inventoryRepository;
  }

  @GetMapping("/" + ORDERS + "/{id}")
  public Order getItem(@PathVariable(value = "id") int id) {
    return orderRepository
        .findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Order not found for this id :: " + id));
  }

  @GetMapping("/" + ORDERS)
  public Iterable<Order> getItem() {
    return orderRepository.findAll();
  }

  @PostMapping("/" + ORDERS)
  @Transactional
  public Order create(@RequestBody OrderView orderView) {
    Order order = createOrder(orderView);
    createOrderItems(orderView, order);
    return order;
  }

  private void createOrderItems(@RequestBody OrderView orderView, Order order) {
    ArrayList<Integer> ids = orderView.getIds();
    ArrayList<Integer> quantities = orderView.getQuantities();

    for (int i = 0; i < ids.size(); i++) {
      int item = ids.get(i);
      int quantity = quantities.get(i);
      Inventory inventory = inventoryRepository.findById(item).orElse(null);

      if (inventory != null && inventory.getQuantity() >= quantity) {
        inventory.setQuantity(inventory.getQuantity() - quantity);
        inventoryRepository.save(inventory);
        orderItemRepository.save(new OrderItem(inventory, order, quantity));
      } else
        throw new InventoryNotSufficientException(
            "Inventory not sufficient -> item: " + item + " q: " + quantity);
    }
  }

  private Order createOrder(@RequestBody OrderView orderView) {
    Order order = new Order();

    order.setCustomerEmail(orderView.getCustomerEmail());
    order.setStatus(orderView.getStatus());
    order.setDate(new Date());
    order = orderRepository.save(order);
    return order;
  }

  @PutMapping("/" + ORDERS + "/{id}")
  @Transactional
  public Order update(@PathVariable(value = "id") int id, @RequestBody OrderView orderView) {
    Order order =
        orderRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Order not found for this id :: " + id));

    order.setCustomerEmail(orderView.getCustomerEmail());
    orderItemRepository.deleteOrderItemsByOrder_IdIs(order.getId());
    return orderRepository.save(order);
  }

  @DeleteMapping("/" + ORDERS + "/{id}")
  public boolean delete(@PathVariable(value = "id") int id) {
    Order order =
        orderRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Order not found for this id :: " + id));

    orderRepository.delete(order);
    return true;
  }
}
