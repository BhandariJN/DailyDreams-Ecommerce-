package com.dailydreams.dailydreams.repository;

import com.dailydreams.dailydreams.model.Image;
import com.dailydreams.dailydreams.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findByProductId(Long id);
}
