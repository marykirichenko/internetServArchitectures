package com.example.isa.service;
import com.example.isa.entity.Brand;
import com.example.isa.entity.Category;
import com.example.isa.entity.Product;
import com.example.isa.entity.SkinType;
import com.example.isa.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.*;
import java.util.*;
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository){
        this.productRepository = productRepository;
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public List<Product> findByPrice(int price) {
        return productRepository.findByPrice(price);
    }

    public Optional<List<Product>> findByCategoryName(String categoryName) {
        return productRepository.findByCategoryName(categoryName);
    }

    public Optional<List<Product>> findByBrandName(String brandName) {
        return productRepository.findByBrandName(brandName);
    }

    public Optional<List<Product>> findBySkinTypeName(String skinTypeName) {
        return productRepository.findBySkinTypeName(skinTypeName);
    }

    public Optional<Product> findByUUID(UUID uuid) {
        return productRepository.findById(uuid);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteByUUID(UUID uuid) {
        productRepository.deleteById(uuid);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public boolean isProductUnique(String name, Category category,Brand brand, SkinType skinType) {
        return !productRepository.existsByNameAndCategoryAndBrandAndSkinType(name, category, brand, skinType);
    }
}
