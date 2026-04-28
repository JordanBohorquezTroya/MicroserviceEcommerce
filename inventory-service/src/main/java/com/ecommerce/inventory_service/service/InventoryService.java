package com.ecommerce.inventory_service.service;

import com.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.ecommerce.inventory_service.dto.InventoryResponseDTO;
import java.util.List;

public interface InventoryService {
    boolean isInStock (String sku, Integer quantity);
    InventoryResponseDTO createInventory (InventoryRequestDTO inventoryRequestDTO);
    List<InventoryResponseDTO> getAllsInventorys();
    InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO inventoryRequestDTO);
    void deleteInvetory(Long id);

}
