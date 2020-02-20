package com.example.inventory.demo;

import com.example.inventory.demo.models.Inventory;
import com.example.inventory.demo.models.Order;
import com.example.inventory.demo.models.OrderView;
import com.example.inventory.demo.repository.InventoryRepository;
import com.example.inventory.demo.repository.OrderItemRepository;
import com.example.inventory.demo.repository.OrderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.inventory.demo.OrderController.ORDERS;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class OrderControllerTests {

  public static final String CUSTOMEREMAIL = "$.customerEmail";
  public static final String PREFIX = "/";
  public static ArrayList<Inventory> inventories;
  public static OrderView orderView;
  @Autowired InventoryRepository inventoryRepository;
  @Autowired OrderRepository orderRepository;
  @Autowired OrderItemRepository orderItemRepository;
  @Autowired MockMvc mvc;

  @BeforeAll
  static void setupClass() {
    inventories = new ArrayList<>();

    inventories.add(new Inventory.Builder().Name("item1").quantity(100).build());
    inventories.add(new Inventory.Builder().Name("item2").quantity(100).build());
    inventories.add(new Inventory.Builder().Name("item3").quantity(100).build());

    orderView = new OrderView();
    orderView.setCustomerEmail("test@test.com");
    orderView.setIds(new ArrayList<>(List.of(1, 2, 3)));
    orderView.setQuantities(new ArrayList<>(List.of(5, 10, 15)));
  }

  @BeforeEach
  public void setupTest() {
    inventoryRepository.saveAll(inventories);
  }

  public <T> List<T> getListFromIterator(Iterator<T> iterator) {
    Iterable<T> iterable = () -> iterator;

    return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
  }

  @Test
  public void simpleOrderAdd() throws Exception {
    mvc.perform(
            post(PREFIX + ORDERS)
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderView)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath(CUSTOMEREMAIL, is(orderView.getCustomerEmail())));
  }

  @Test
  public void simpleOrderGetAllCall() throws Exception {
    mvc.perform(
            post(PREFIX + ORDERS)
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderView)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath(CUSTOMEREMAIL, is(orderView.getCustomerEmail())));

    mvc.perform(get(PREFIX + ORDERS))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].customerEmail", is(orderView.getCustomerEmail())));
  }

  @Test
  public void simpleInventoryGetSingleItemCall() throws Exception {
    mvc.perform(
            post(PREFIX + ORDERS)
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderView)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath(CUSTOMEREMAIL, is(orderView.getCustomerEmail())));

    Order order = orderRepository.findAll().iterator().next();
    mvc.perform(get(PREFIX + ORDERS + "/" + order.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath(CUSTOMEREMAIL, is(orderView.getCustomerEmail())));
  }

  @Test
  public void simpleInventoryDeleteSingleItemCall() throws Exception {
    simpleOrderGetAllCall();

    Order order = orderRepository.findAll().iterator().next();

    assert (orderItemRepository.count() == 3);
    mvc.perform(delete(PREFIX + ORDERS + "/" + order.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("true"));
    assert (orderItemRepository.count() == 0);

    mvc.perform(get(PREFIX + ORDERS))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void simpleInventoryUpdateItemCall() throws Exception {
    simpleOrderGetAllCall();

    orderView.setCustomerEmail("tata@tata.com");

    Order order = orderRepository.findAll().iterator().next();
    mvc.perform(
            put(PREFIX + ORDERS + "/" + order.getId())
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(orderView)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath(CUSTOMEREMAIL, is(orderView.getCustomerEmail())));

    mvc.perform(get(PREFIX + ORDERS + "/" + order.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath(CUSTOMEREMAIL, is(orderView.getCustomerEmail())));
  }
}
