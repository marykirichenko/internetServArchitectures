package com.example.isa.service;

import com.example.isa.entity.Category;
import com.example.isa.entity.Product;
import com.example.isa.repository.CategoryRepository;
import java.util.*;
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
    public Optional<Category> findByCode(Integer code){
        return categoryRepository.findByCode(code);
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
    public void save(Category category){
        categoryRepository.save(category);
    }

    public Optional<List<Product>> getAllProductsFromCategory(String name) {
        return categoryRepository.findProductsByCategory(name);
    }
}
