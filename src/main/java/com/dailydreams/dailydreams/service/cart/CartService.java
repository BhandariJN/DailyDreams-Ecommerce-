package com.dailydreams.dailydreams.service.cart;


import com.dailydreams.dailydreams.Dtos.CartDto;
import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.Cart;
import com.dailydreams.dailydreams.model.User;
import com.dailydreams.dailydreams.repository.CartItemRepository;
import com.dailydreams.dailydreams.repository.CartRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
@Service
public class CartService implements ICartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final AtomicLong cartIdGenerator = new AtomicLong(0);
    private final ModelMapper modelMapper;
   // EntityManager entityManager;

    @Override
    public Cart getCart(Long id) {
        Cart cart =cartRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Cart Not Found"));
        BigDecimal totalAmount = cart.getTotalAmount();
        cart.setTotalAmount (totalAmount);
        return cartRepository.save(cart);
    }

    @Transactional
    @Override
    public void clearCart(Long id) {
        Cart cart = getCart(id);
        cartItemRepository.deleteAllByCartId(id);
        cart.getCartItems().clear();
        cartRepository.deleteById(id);

    }



    @Override
    public BigDecimal getTotalPrice(Long id) {
        Cart cart = getCart(id);


        return  cart.getTotalAmount();

    }


    @Override
    public Cart cartInitializer(User user) {


        return Optional.ofNullable(getCartByUserId(user.getUserId()))
                .orElseGet(()->{
                    Cart cart = new Cart();
                    cart.setUser(user);
                    return cartRepository.save(cart);
                });

    }

    @Override
    public Cart getCartByUserId(Long userID) {
        return cartRepository.findByUserUserId(userID);
    }

    @Override
    public CartDto toCartDto(Cart cart) {
       return modelMapper.map(cart, CartDto.class);
    }
}
