package com.dailydreams.dailydreams.repository;

import com.dailydreams.dailydreams.model.Category;
import com.dailydreams.dailydreams.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
     Category findByName(String name);

    boolean existsByName(String name);
}
