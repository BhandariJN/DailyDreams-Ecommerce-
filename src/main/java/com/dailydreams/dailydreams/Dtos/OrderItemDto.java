package com.dailydreams.dailydreams.Dtos;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {
    private Long id;
    private String productName;
    private String productBrand;
    private int quantity;
    private BigDecimal price;
}
