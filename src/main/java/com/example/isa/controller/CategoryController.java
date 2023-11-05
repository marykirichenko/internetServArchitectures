package com.example.isa.controller;

import com.example.isa.dto.createUpdate.CategoryCreateUpdateDTO;
import com.example.isa.dto.read.CategoryReadDTO;
import com.example.isa.dto.readCollection.ProductReadCollectionDTO;
import com.example.isa.entity.Category;
import com.example.isa.entity.Product;
import com.example.isa.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/categories")
@Validated
public class CategoryController {
    private final CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryReadDTO> getAllCategories(){
        return categoryService.getAllCategories().stream().map(this::convertToReadDTO).collect(Collectors.toList());

    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryReadDTO getCategoryByName(@PathVariable String name){
        Optional<Category> categoryOptional = categoryService.findByName(name);
        return categoryOptional.map(this::convertToReadDTO).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
    }

    @GetMapping("/{name}/products")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductReadCollectionDTO> getProductsFromCategory(@PathVariable String name){
        Optional<List<Product>> productsOptional = categoryService.getAllProductsFromCategory(name);
        if(productsOptional.isPresent() && categoryService.findByName(name).isPresent()){
            return productsOptional.get().stream().map(this::convertToProductDTO).collect(Collectors.toList());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createCategory(@Valid @RequestBody CategoryCreateUpdateDTO category){
        Optional<Category> existingCategory = categoryService.findByName(category.getName());
        Optional<Category> existingCategoryCode = categoryService.findByCode(category.getCode());
        if(existingCategory.isPresent() || existingCategoryCode.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category already exists");
        }else{
            categoryService.save(convertToEntity(category));
        }
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void updateCategory(@PathVariable UUID uuid, @Valid @RequestBody CategoryCreateUpdateDTO category){
        Optional<Category> existingCategory = categoryService.findByUUID(uuid);
        if(existingCategory.isPresent()){
            Category entityCategory = convertToEntity(category);
            entityCategory.setUuid(uuid);
            categoryService.save(entityCategory);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteCategory(@PathVariable UUID uuid){
        Optional<Category> existingCategory = categoryService.findByUUID(uuid);
        if(existingCategory.isPresent()){
            categoryService.deleteByUUID(uuid);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");
        }
    }

    public CategoryReadDTO convertToReadDTO(Category category){
        return new CategoryReadDTO(category.getUuid(), category.getName(), category.getCode());
    }
    public ProductReadCollectionDTO convertToProductDTO(Product product){
        return new ProductReadCollectionDTO(product.getUuid(), product.getName(), product.getPrice());
    }

    public Category convertToEntity(CategoryCreateUpdateDTO category){
        return new Category(category.getName(), category.getCode());
    }
}
