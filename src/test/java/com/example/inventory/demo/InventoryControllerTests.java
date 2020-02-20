package com.example.inventory.demo;

import com.example.inventory.demo.models.Inventory;
import com.example.inventory.demo.repository.InventoryRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static com.example.inventory.demo.InventoryController.INVENTORIES;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class InventoryControllerTests {

  public static final String PREFIX = "/";
  public static Inventory testInvItem;
  @Autowired InventoryRepository inventoryRepository;
  @Autowired MockMvc mvc;

  public <T> List<T> getListFromIterator(Iterator<T> iterator) {
    Iterable<T> iterable = () -> iterator;

    return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toList());
  }

  @BeforeAll
  public static void setupTestItem() {
    testInvItem = new Inventory();
    testInvItem.setPrice(1);
    testInvItem.setName("testInvItem");
  }

  @Test
  public void simpleInventoryAdd() {
    InventoryController inventoryController = new InventoryController(inventoryRepository);
    inventoryController.create(testInvItem);
    assert getListFromIterator(inventoryController.getItems().iterator()).size() == 1;
  }

  @Test
  public void simpleInventoryGetAllCall() throws Exception {
    mvc.perform(
        post(PREFIX + INVENTORIES)
            .contentType(APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testInvItem)));

    mvc.perform(get(PREFIX + INVENTORIES))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].name", is(testInvItem.getName())))
        .andExpect(jsonPath("$[0].price", is(testInvItem.getPrice())));
  }

  @Test
  public void simpleInventoryGetSingleItemCall() throws Exception {
    mvc.perform(
        post(PREFIX + INVENTORIES)
            .contentType(APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(testInvItem)));

    mvc.perform(get(PREFIX + INVENTORIES + "/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.name", is(testInvItem.getName())))
        .andExpect(jsonPath("$.price", is(testInvItem.getPrice())));
  }

  @Test
  public void simpleInventoryDeleteSingleItemCall() throws Exception {
    simpleInventoryGetAllCall();

    mvc.perform(delete(PREFIX + INVENTORIES + "/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string("true"));

    mvc.perform(get(PREFIX + INVENTORIES))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$", hasSize(0)));
  }

  @Test
  public void simpleInventoryUpdateItemCall() throws Exception {
    simpleInventoryGetAllCall();

    testInvItem.setName("newName");

    mvc.perform(
            put(PREFIX + INVENTORIES + "/1")
                .contentType(APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(testInvItem)))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.name", is(testInvItem.getName())))
        .andExpect(jsonPath("$.price", is(testInvItem.getPrice())));

    mvc.perform(get(PREFIX + INVENTORIES + "/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.name", is(testInvItem.getName())))
        .andExpect(jsonPath("$.price", is(testInvItem.getPrice())));
  }
}
