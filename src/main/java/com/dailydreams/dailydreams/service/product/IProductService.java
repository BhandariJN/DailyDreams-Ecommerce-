package com.dailydreams.dailydreams.service.product;


import com.dailydreams.dailydreams.Dtos.ProductDto;
import com.dailydreams.dailydreams.model.Product;
import com.dailydreams.dailydreams.request.AddProductRequest;
import com.dailydreams.dailydreams.request.ProductUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProductService {

    Product addProduct(AddProductRequest request);

    Product getProductById(Long id);
   void deleteProductById(Long id);
   Product updateProduct(ProductUpdateRequest request, Long productId);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String Brand, String name);


    List<ProductDto> convertToProductDto(List<Product> products);

    ProductDto convertProductToProductDto(Product product);
}
