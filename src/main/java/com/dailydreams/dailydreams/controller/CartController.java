package com.dailydreams.dailydreams.controller;


import com.dailydreams.dailydreams.Dtos.CartDto;
import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.Cart;
import com.dailydreams.dailydreams.response.ApiResponse;
import com.dailydreams.dailydreams.service.cart.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/carts")
public class CartController {
    private final CartService cartService;

    @GetMapping("/{userId}/myCart")
    public ResponseEntity<ApiResponse> getCart(@PathVariable Long userId) {
        try {
            Cart cart = cartService.getCartByUserId(userId);
            CartDto cartDto = cartService.toCartDto(cart);
            return ResponseEntity.ok(new ApiResponse("success", cartDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }


    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable Long cartId) {
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("cart clear success", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

    @GetMapping("/cart/{cartId}/total-amount")
    public ResponseEntity<ApiResponse> getTotalAmount(@PathVariable Long cartId) {
        try {
            BigDecimal totalAmount = cartService.getTotalPrice(cartId);
            return ResponseEntity.ok(new ApiResponse("total amount", totalAmount));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

}
