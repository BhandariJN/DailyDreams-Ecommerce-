package com.dailydreams.dailydreams.controller;

import com.dailydreams.dailydreams.exception.AlreadyExistsException;
import com.dailydreams.dailydreams.exception.ResourceNotFoundException;
import com.dailydreams.dailydreams.model.Category;
import com.dailydreams.dailydreams.response.ApiResponse;
import com.dailydreams.dailydreams.service.category.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService
            categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }


    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new ApiResponse("Found", categories));
        } catch (Exception e) {
        return    ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error:",HttpStatus.INTERNAL_SERVER_ERROR));
        }

    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addCategory(@RequestBody Category name) {

        try {
            Category theCategory = categoryService.addCategory(name);
            return  ResponseEntity.ok(new ApiResponse("Added", theCategory));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(),null));

       }
    }


    @GetMapping("/category/{categoryId}/category")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable Long categoryId) {

        try {
            Category category = categoryService.getCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Found", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));


        }
    }

        @GetMapping("category/{name}/category")
        public ResponseEntity<ApiResponse> getCategoryByName(@PathVariable String name){

            try {
                Category category = categoryService.getCategoryByName(name);
                return ResponseEntity.ok(new ApiResponse("Found", category));
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));


            }
        }
    @DeleteMapping("/category/{categoryId}/delete")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Long categoryId) {

        try {
            categoryService.deleteCategoryById(categoryId);
            return ResponseEntity.ok(new ApiResponse("Deleted", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));


        }
    }



        @PutMapping("category/{Id}/update")
        public ResponseEntity<ApiResponse> updateCategory(@PathVariable Long Id, @RequestBody Category category ) {

            try {
                Category theCategory = categoryService.updateCategory(category, Id);
                return ResponseEntity.ok(new ApiResponse("Update Success", theCategory));
            } catch (ResourceNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
            }


        }
}
