package com.ecommerce.inventory_service.controller;


import com.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.ecommerce.inventory_service.dto.InventoryResponseDTO;
import com.ecommerce.inventory_service.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{sku}")
    public boolean isInStock (@PathVariable("sku") String sku, @RequestParam("quantity") Integer quantity) {
        return inventoryService.isInStock(sku, quantity);
    }

    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventory (@RequestBody @Valid InventoryRequestDTO inventoryRequestDTO){
         return ResponseEntity.status(HttpStatus.CREATED).body(inventoryService.createInventory(inventoryRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getInventoryAll (){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.getAllsInventorys());
    }


    @PutMapping("/{id}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryRequestDTO inventoryRequestDTO){
        return ResponseEntity.status(HttpStatus.OK).body(inventoryService.updateInventory(id,inventoryRequestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInvetory(id);
        return ResponseEntity.noContent().build();
    }



}
