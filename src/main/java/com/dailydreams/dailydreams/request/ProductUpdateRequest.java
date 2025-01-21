package com.dailydreams.dailydreams.request;

import com.dailydreams.dailydreams.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductUpdateRequest {

    private Long id;
    private String brand;
    private String description;
    private BigDecimal price;
    private int inventory;
    private String name;
    private Category category;
}
