package com.dailydreams.dailydreams.controller;

import com.dailydreams.dailydreams.Dtos.OrderDto;
import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.Order;
import com.dailydreams.dailydreams.response.ApiResponse;
import com.dailydreams.dailydreams.service.order.IOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    private final IOrderService orderService;


    @PostMapping("/order")
    public ResponseEntity<ApiResponse> createOrder(@RequestParam Long userId) {
        try {
            Order order = orderService.placeOrder(userId);
            System.out.println(order);
            OrderDto orderDto = orderService.covertToOrderDto(order);

            return ResponseEntity.ok().body(new ApiResponse("Item Order success!", orderDto));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Error while creating order!"+e.getMessage(), null));
        }


    }

    @GetMapping("/{orderId}/order")
    public ResponseEntity<ApiResponse> getOrderById( @PathVariable Long orderId) {
        try {
            OrderDto order = orderService.getOrder(orderId);
            return ResponseEntity.ok().body(new ApiResponse("success!", order));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }


    }

    @GetMapping("/{userId}/user-order")
    public ResponseEntity<ApiResponse> getUserOrder( @PathVariable Long userId) {
        try {
            List<OrderDto> orders = orderService.getUserOrders(userId);
            return ResponseEntity.ok().body(new ApiResponse("success!", orders));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }


    }

}
