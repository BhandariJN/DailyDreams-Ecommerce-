package com.dailydreams.dailydreams.Dtos;

import com.dailydreams.dailydreams.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProductDto {


    private Long id;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private String name;
        private Category category;

    private List<ImageDto> images;
}
