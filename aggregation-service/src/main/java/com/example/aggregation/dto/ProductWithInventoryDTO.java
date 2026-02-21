package com.example.aggregation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductWithInventoryDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private InventoryInfo inventory;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class InventoryInfo {
        private Integer quantity;
        private Boolean available;
        private String status; 
    }
}
