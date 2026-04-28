package com.ecommerce.inventory_service.mapper;

import com.ecommerce.inventory_service.dto.InventoryRequestDTO;
import com.ecommerce.inventory_service.dto.InventoryResponseDTO;
import com.ecommerce.inventory_service.model.Inventory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface InventoryMapper {

        Inventory toInventory (InventoryRequestDTO inventoryRequestDTO);

        //@Mapping(target = "inStock", expression = "java(inventory.getQuantity() > 0")
        InventoryResponseDTO toInventoryResponseDTO (Inventory product);

        //@Mapping(target = "id", ignore = true)
        void updateInventoryRequest (InventoryRequestDTO inventoryRequestDTO, @MappingTarget Inventory inventory);

}
