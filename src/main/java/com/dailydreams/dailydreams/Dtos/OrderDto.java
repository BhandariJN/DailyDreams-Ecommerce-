package com.dailydreams.dailydreams.Dtos;

import com.dailydreams.dailydreams.enums.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Data
public class OrderDto {

    private Long orderId;
    private LocalDate orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Set<OrderItemDto> orderItems;

}
