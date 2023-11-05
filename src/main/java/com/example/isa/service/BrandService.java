package com.example.isa.service;

import com.example.isa.entity.Brand;
import com.example.isa.entity.Product;
import com.example.isa.repository.BrandRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class BrandService {
    private final BrandRepository brandRepository;
    @Autowired
    public BrandService(BrandRepository brandRepository){
        this.brandRepository = brandRepository;
    }

    public void deleteByUUID(UUID uuid) {
        brandRepository.deleteById(uuid);
    }

    public List<Brand> getAllBrands(){
        return brandRepository.findAll();
    }

    public Optional<Brand> findByName(String name){
        return brandRepository.findByName(name);
    }
    public Optional<List<Product>> getAllProductsFromBrand(String name) {
        return brandRepository.findProductsByBrandName(name);
    }

    public Optional<Brand> findByUUID(UUID uuid){
        return brandRepository.findById(uuid);
    }
    public void save(Brand brand){
        brandRepository.save(brand);
    }
}
