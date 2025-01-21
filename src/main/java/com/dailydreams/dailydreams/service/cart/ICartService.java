package com.dailydreams.dailydreams.service.cart;

import com.dailydreams.dailydreams.Dtos.CartDto;
import com.dailydreams.dailydreams.model.Cart;
import com.dailydreams.dailydreams.model.User;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface ICartService {
      Cart getCart(Long id);
      void  clearCart(Long id);
    BigDecimal getTotalPrice(Long id);



    Cart cartInitializer(User user);

    Cart getCartByUserId(Long userID);

    CartDto toCartDto(Cart cart);
}
