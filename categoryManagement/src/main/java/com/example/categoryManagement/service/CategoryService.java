package com.example.categoryManagement.service;
import com.example.categoryManagement.dto.CategoryReadDTO;
import com.example.categoryManagement.entity.Category;
import com.example.categoryManagement.repository.CategoryRepository;

import java.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final RestTemplate restTemplate;
    @Autowired
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
        this.restTemplate = new RestTemplate();
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

    public void notifyProductsService(String categoryName, String action) {
        String productsUrl = "http://localhost:8080/api/product/products/categoryNotifications";

        MultiValueMap<String, Object> requestPayload = new LinkedMultiValueMap<>();
        requestPayload.add("categoryName", categoryName);
        requestPayload.add("action", action);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestPayload, headers);

        restTemplate.postForObject(productsUrl, requestEntity, String.class);
    }

}
