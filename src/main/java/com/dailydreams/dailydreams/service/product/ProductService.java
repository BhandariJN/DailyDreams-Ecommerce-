package com.dailydreams.dailydreams.service.product;

import com.dailydreams.dailydreams.Dtos.ImageDto;
import com.dailydreams.dailydreams.Dtos.ProductDto;
import com.dailydreams.dailydreams.exception.AlreadyExistsException;
import com.dailydreams.dailydreams.exception.ProductNotFoundException;
import com.dailydreams.dailydreams.model.Category;
import com.dailydreams.dailydreams.model.Image;
import com.dailydreams.dailydreams.model.Product;
import com.dailydreams.dailydreams.repository.CategoryRepository;
import com.dailydreams.dailydreams.repository.ImageRepository;
import com.dailydreams.dailydreams.repository.ProductRepository;
import com.dailydreams.dailydreams.request.AddProductRequest;
import com.dailydreams.dailydreams.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class ProductService implements IProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;


    @Override
    public Product addProduct(AddProductRequest request) {
        // Check if the category is found in DB
        // If yes, set it as a new Product Category
        // If no, save it as a new category and set it as the Product Category

        if(productExists(request.getName(), request.getBrand())){
            throw new AlreadyExistsException("Product "+request.getName()+" and "+request.getBrand()+ " already exists"+",you may update instead");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(request.getCategory().getName()))
                .orElseGet(() -> {
                    // If the category does not exist, create a new category and save it
                    Category newCategory = new Category(request.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });

        // Set the found or newly created category into the request's category field
        request.setCategory(category);

        // Save the product using the provided request and category, and return the saved product
        return productRepository.save(createProduct(request, category));
    }


    private Product createProduct(AddProductRequest request, Category category) {
        return new Product(
                request.getName(),
                request.getBrand(),
                request.getPrice(),
                request.getInventory(),
                request.getDescription(),
                category
        );
    }

    private boolean productExists(String name, String brand) {
        return productRepository.existsByNameAndBrand(name,brand);
    }


    @Override
    public Product getProductById(Long id) {
        return productRepository.
                findById(id).
                orElseThrow(()->new ProductNotFoundException("Product Not Found!"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id)
                .ifPresentOrElse(productRepository::delete,
                        ()-> { throw new ProductNotFoundException("Product Not Found!");});


    }

    @Override
    public Product updateProduct(ProductUpdateRequest request, Long productId) {

        return productRepository.findById(productId)
                .map(existingProduct->updateExistingProduct(existingProduct,request))
                .map(productRepository::save)
                .orElseThrow(()->new ProductNotFoundException("Product Not Found!"));



    }

    private Product updateExistingProduct(Product existingProduct, ProductUpdateRequest request ){

        request.setName(existingProduct.getName());
        request.setBrand(existingProduct.getBrand());
        request.setPrice(existingProduct.getPrice());
        request.setInventory(existingProduct.getInventory());
        request.setDescription(existingProduct.getDescription());

        Category category = categoryRepository.findByName(request.getCategory().getName());
        request.setCategory(category);

        return existingProduct;

    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category,brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand,name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);

    }

    @Override
    public List<ProductDto> convertToProductDto(List<Product> products) {
        return products.stream().map(this::convertProductToProductDto).toList();
    }

    @Override
    public ProductDto convertProductToProductDto(Product product) {
        ProductDto productDto = modelMapper.map(product, ProductDto.class);
        List<Image> images = imageRepository.findByProductId(product.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;
    }
}
