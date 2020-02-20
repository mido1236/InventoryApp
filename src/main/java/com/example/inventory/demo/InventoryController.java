package com.example.inventory.demo;

import com.example.inventory.demo.models.Inventory;
import com.example.inventory.demo.repository.InventoryRepository;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {

  public static final String INVENTORIES = "inventories";
  private final InventoryRepository inventoryRepository;

  public InventoryController(InventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
  }

  @GetMapping("/" + INVENTORIES + "/{id}")
  public Inventory getItem(@PathVariable(value = "id") int id) {
    return inventoryRepository
        .findById(id)
        .orElseThrow(
            () -> new ResourceNotFoundException("Inventory not found for this id :: " + id));
  }

  @GetMapping("/" + INVENTORIES)
  public Iterable<Inventory> getItems() {
    return inventoryRepository.findAll();
  }

  @PostMapping("/" + INVENTORIES)
  public Inventory create(@RequestBody Inventory inventory) {
    return inventoryRepository.save(inventory);
  }

  @PutMapping("/" + INVENTORIES + "/{id}")
  public Inventory update(
      @PathVariable(value = "id") int id, @RequestBody Inventory inventoryDetails) {
    Inventory inventory =
        inventoryRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Inventory not found for this id :: " + id));

    inventory.setDescription(inventoryDetails.getDescription());
    inventory.setName(inventoryDetails.getName());
    inventory.setQuantity(inventoryDetails.getQuantity());
    return inventoryRepository.save(inventory);
  }

  @DeleteMapping("/" + INVENTORIES + "/{id}")
  public boolean delete(@PathVariable(value = "id") int id, Inventory inventoryDetails) {
    Inventory inventory =
        inventoryRepository
            .findById(id)
            .orElseThrow(
                () -> new ResourceNotFoundException("Inventory not found for this id :: " + id));

    inventoryRepository.delete(inventory);
    return true;
  }
}
