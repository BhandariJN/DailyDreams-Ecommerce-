package com.dailydreams.dailydreams.repository;

import com.dailydreams.dailydreams.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserUserId(Long userID);
}
