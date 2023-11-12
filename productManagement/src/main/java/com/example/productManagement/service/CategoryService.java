package com.example.productManagement.service;

import java.util.*;

import com.example.productManagement.entity.Category;
import com.example.productManagement.entity.Product;
import com.example.productManagement.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }
    public Optional<Category> findByName(String name){
        return categoryRepository.findByName(name);
    }

    public Optional<Category> findByUUID(UUID uuid){
        return categoryRepository.findById(uuid);
    }
    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }
    public void deleteByUUID(UUID uuid){
        categoryRepository.deleteById(uuid);
    }
    public void deleteByName(String name){
        categoryRepository.deleteByName(name);
    }
    public void save(Category category){
        categoryRepository.save(category);
    }

    public Optional<List<Product>> getAllProductsFromCategory(String name) {
        return categoryRepository.findProductsByCategory(name);
    }
}
