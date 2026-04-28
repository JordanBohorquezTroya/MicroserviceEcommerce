package com.ecommerce.inventory_service.service.impl;

import com.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.ecommerce.inventory_service.dto.InventoryResponseDTO;
import com.ecommerce.inventory_service.exception.ResourceNotFoundException;
import com.ecommerce.inventory_service.mapper.InventoryMapper;
import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import com.ecommerce.inventory_service.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    @Override
    @Transactional(readOnly = true)
    public boolean isInStock(String sku, Integer quantity) {
        return inventoryRepository.findBySku(sku)
                .map(inventory -> inventory.getQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    @Transactional
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO) {
        boolean exists = inventoryRepository.existsBySku(inventoryRequestDTO.sku());
        if(exists){
            throw new RuntimeException("El inventario para el sku " + inventoryRequestDTO.sku() + " ya existe");
        }
        Inventory inventory = inventoryMapper.toInventory(inventoryRequestDTO);
        Inventory inventorySave = inventoryRepository.save(inventory);
        log.info("Inventario {} , guardado ", inventorySave.getSku());
        return inventoryMapper.toInventoryResponseDTO(inventorySave);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponseDTO> getAllsInventorys() {
        return inventoryRepository.findAll().stream().map(inventoryMapper::toInventoryResponseDTO).toList();
    }



    @Override
    @Transactional
    public InventoryResponseDTO updateInventory(Long id, InventoryRequestDTO InventoryRequestDTO) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if (!inventory.isPresent())   {
            throw new ResourceNotFoundException("Inventario", "id", id);
        }

        inventoryMapper.updateInventoryRequest(InventoryRequestDTO, inventory.get());
        Inventory inventorySave = inventoryRepository.save(inventory.get());
        log.info("Inventario {} , actualizado ", inventorySave.getSku());
        return inventoryMapper.toInventoryResponseDTO(inventorySave);
    }

    @Override
    @Transactional
    public void deleteInvetory(Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if (!inventory.isPresent()) {
            throw new ResourceNotFoundException("Inventario", "id", id);
        }
        inventoryRepository.deleteById(id);
        log.info("Inventario {} , eliminado ", inventory.get().getSku());

    }
}
