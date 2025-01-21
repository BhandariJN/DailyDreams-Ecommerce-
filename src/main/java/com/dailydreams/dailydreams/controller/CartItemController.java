package com.dailydreams.dailydreams.controller;

import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.Cart;
import com.dailydreams.dailydreams.model.User;
import com.dailydreams.dailydreams.response.ApiResponse;
import com.dailydreams.dailydreams.service.cart.ICartService;
import com.dailydreams.dailydreams.service.cartIem.ICartItemService;
import com.dailydreams.dailydreams.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/cartItems")

public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final UserService userService;


    @PostMapping("/item/add")
    public ResponseEntity<ApiResponse> addIemToCartItem(@RequestParam Long productId, @RequestParam int quantity) {
        try {
            User user = userService.getUserById(4L);
            Cart cart = cartService.cartInitializer(user);


            cartItemService.addItemToCart(cart.getId(),productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Item Add Success!", null));
        } catch (ResourceNotFoundException e) {
return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }


    @DeleteMapping("/cart/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable  Long cartId, @PathVariable Long itemId) {
        try {

            cartItemService.removeItemFromCart(cartId,itemId);
            return ResponseEntity.ok(new ApiResponse("Item Remove Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @PutMapping("cart/{cartId}/item/{productId}/update")
    public ResponseEntity<ApiResponse> updateItemQuantity(@PathVariable Long cartId,
                                                          @PathVariable Long productId,
                                                          @RequestParam int quantity)
    {
        try {
            cartItemService.updateItemQuantity(cartId,productId,quantity);
            return ResponseEntity.ok(new ApiResponse("Item Update Success!", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }

}
