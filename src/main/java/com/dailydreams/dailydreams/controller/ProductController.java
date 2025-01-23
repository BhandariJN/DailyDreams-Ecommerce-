package com.dailydreams.dailydreams.controller;


import com.dailydreams.dailydreams.Dtos.ProductDto;
import com.dailydreams.dailydreams.exception.AlreadyExistsException;
import com.dailydreams.dailydreams.exception.ProductNotFoundException;
import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.Product;
import com.dailydreams.dailydreams.request.AddProductRequest;
import com.dailydreams.dailydreams.request.ProductUpdateRequest;
import com.dailydreams.dailydreams.response.ApiResponse;
import com.dailydreams.dailydreams.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/product")
public class ProductController {

private final IProductService productService;

    public ProductController(IProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/all")
public ResponseEntity<ApiResponse> allProducts() {

    List<Product> products = productService.getAllProducts();

    List<ProductDto> convertedProducts = productService.convertToProductDto(products);
    return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
}

@GetMapping("product/product/{productId}")
 public ResponseEntity<ApiResponse> getProductById(@PathVariable Long productId) {

     try {
         Product product = productService.getProductById(productId);
         ProductDto convertedProduct = productService.convertProductToProductDto(product);

         return ResponseEntity.ok(new ApiResponse("success", convertedProduct));
     } catch (ResourceNotFoundException e) {
         return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
     }

 }


 @PreAuthorize("hasRole('ROLE_ADMIN')")
 @PostMapping("/add")
 public ResponseEntity<ApiResponse> addProduct(@RequestBody AddProductRequest product){

     try {
         Product theProduct = productService.addProduct(product);
         return ResponseEntity.ok(new ApiResponse("add product success", theProduct));
     } catch (AlreadyExistsException e) {

         return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
     }
 }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
  @PutMapping("/product/{productId}/update")
public  ResponseEntity<ApiResponse> updateProduct(@RequestBody ProductUpdateRequest product, @PathVariable Long productId){

    try {
        Product theProduct = productService.updateProduct(product, productId);
        ProductDto convertedProduct = productService.convertProductToProductDto(theProduct);
        return ResponseEntity.ok(new ApiResponse("update product success", convertedProduct));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));

    }
}


    @PreAuthorize("hasRole('ROLE_ADMIN')")
@DeleteMapping("/product/{productId}/delete")
public ResponseEntity<ApiResponse> deleteProduct(@PathVariable Long productId){

    try {
        productService.deleteProductById(productId);
        return ResponseEntity.ok(new ApiResponse("delete product success", productId));
    } catch (ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
    }


}


@GetMapping("product/by/brand-and-name")
public ResponseEntity<ApiResponse> getProductByBrandAndName(@RequestParam String brandName, @RequestParam String name){


    try{
        List<Product> products = productService.getProductsByBrandAndName(brandName, name);
        if (products.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Products Found", products));
        }
        List<ProductDto> convertedProducts = productService.convertToProductDto(products);
                return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
    }
}


    @GetMapping("product/by/category-and-brand")
    public ResponseEntity<ApiResponse> getProductByCategoryAndBrand(@RequestParam String category, @RequestParam String brandName){


        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category,brandName);
            if (products.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Products Found", products));
            }
            List<ProductDto> convertedProducts = productService.convertToProductDto(products);

            return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
        }
    }
 @GetMapping("/product/{name}/product")
    public ResponseEntity<ApiResponse> getProductByName(@PathVariable String name) {
     try {
         List<Product> products = productService.getProductByName(name);
         if (products.isEmpty()) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Products Found", products));

         }
         List<ProductDto> convertedProducts = productService.convertToProductDto(products);

         return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
     } catch (Exception e) {

         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
     }
 }
     @GetMapping("product/by-brand")
     public ResponseEntity<ApiResponse> productByBrandName(@RequestParam String brand) {
         try {
             List<Product> products = productService.getProductsByBrand(brand);
             if (products.isEmpty()) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Products Found", products));
             }
             List<ProductDto> convertedProducts = productService.convertToProductDto(products);

             return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));

         }
     }

     @GetMapping("/product/{category}/all/products")
     public ResponseEntity<ApiResponse> getAllProductsByCategory(@PathVariable String category) {

         try {
             List<Product>    products = productService.getProductsByCategory(category);
             if (products.isEmpty()) {
                 return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("No Products Found", products));

             }
             List<ProductDto> convertedProducts = productService.convertToProductDto(products);

             return ResponseEntity.ok(new ApiResponse("success", convertedProducts));
         } catch (Exception e) {
             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
         }
     }

     public ResponseEntity<ApiResponse> countByBrandAndName(@RequestParam String brand, @RequestParam String name) {
    try {
        var products = productService.countProductsByBrandAndName(brand,name);
        return ResponseEntity.ok(new ApiResponse("Product Count!", products));

    }
    catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse(e.getMessage(), null));
            }
     }



}

