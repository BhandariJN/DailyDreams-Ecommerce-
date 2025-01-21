package com.dailydreams.dailydreams.repository;

import com.dailydreams.dailydreams.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

    Cart findByUserUserId(Long userID);
}
