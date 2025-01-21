package com.dailydreams.dailydreams.service.order;

import com.dailydreams.dailydreams.Dtos.OrderDto;
import com.dailydreams.dailydreams.model.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IOrderService {
    Order placeOrder(Long userId);

    List<OrderDto> getUserOrders(Long userID);

    OrderDto getOrder(Long orderID);


    OrderDto covertToOrderDto(Order order);
}
