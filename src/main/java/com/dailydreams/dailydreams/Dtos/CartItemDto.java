package com.dailydreams.dailydreams.Dtos;

import lombok.Data;

import java.beans.BeanInfo;

@Data
public class CartItemDto {
    private Long id;
    private Integer quantity;
    private BeanInfo unitPrice;
    private ProductDto productDto;

}
