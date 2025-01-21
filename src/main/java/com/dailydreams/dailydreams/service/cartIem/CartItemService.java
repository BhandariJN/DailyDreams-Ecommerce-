package com.dailydreams.dailydreams.service.cartIem;


import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.Cart;
import com.dailydreams.dailydreams.model.CartItem;
import com.dailydreams.dailydreams.model.Product;
import com.dailydreams.dailydreams.repository.CartItemRepository;
import com.dailydreams.dailydreams.repository.CartRepository;
import com.dailydreams.dailydreams.service.cart.ICartService;
import com.dailydreams.dailydreams.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Service
public class CartItemService implements ICartItemService {
    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final IProductService productService;
    private final ICartService cartService;


    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
        //1. get The Cart
        //2. Get the Product
        //3. check if the product already in cart
        //4. if yes, then increase the quantity with requested quantity
        //5. if No, the initiate a cart Item entry

        Cart cart = cartService.getCart(cartId);
        Product product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId()
                        .equals(productId)).
                        findFirst()
                .orElse(new CartItem());

        if(cartItem.getId()==null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }
        else {
            cartItem.setQuantity(cartItem.getQuantity()+quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);

    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {

        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);



    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {

        Cart cart = cartService.getCart(cartId);
        cart.getCartItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> {
                    item.setQuantity(quantity);
                    item.setUnitPrice(item.getProduct().getPrice());
                    item.setTotalPrice();

                                    });
        BigDecimal totalAmount= cart.getCartItems()
                .stream()
                .map(CartItem::getTotalPrice).
                reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);

    }


    @Override
    public CartItem getCartItem(Long cartId, Long productId) {
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems().stream()
                .filter(cartItem -> cartItem.getProduct().getId().equals(productId))
                .findFirst()
                .orElseThrow(()-> new ResourceNotFoundException("Item not found"));
    }
}
