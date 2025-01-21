package com.dailydreams.dailydreams.repository;

import com.dailydreams.dailydreams.model.CartItem;
import com.dailydreams.dailydreams.service.cart.CartService;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Transactional
    @Modifying
    @Query("DELETE FROM CartItem ci WHERE ci.cart.id = :cartId")
    void deleteAllByCartId(@Param("cartId") Long cartId);

}
