package com.example.productManagement.service;

import com.example.productManagement.entity.Product;
import com.example.productManagement.entity.SkinType;
import com.example.productManagement.repository.SkinTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SkinTypeService {
    private final SkinTypeRepository skinTypeRepository;

    @Autowired
    public SkinTypeService(SkinTypeRepository skinTypeRepository) {
        this.skinTypeRepository = skinTypeRepository;
    }

    public void deleteByUUID(UUID uuid) {
        Optional<SkinType> skinTypeOptional = skinTypeRepository.findById(uuid);
        if (skinTypeOptional.isPresent()) {
            skinTypeRepository.deleteById(uuid);
        }
    }

    public void save(SkinType skinType) {
        skinTypeRepository.save(skinType);
    }

    public List<SkinType> getAllSkinTypes() {
        return skinTypeRepository.findAll();
    }

    public Optional<SkinType> findByName(String name) {
        return skinTypeRepository.findByName(name);
    }

    public Optional<List<Product>> getAllProductsBySkinType(String name) {
        return skinTypeRepository.findProductsBySkinType(name);
    }

    public Optional<SkinType> findByUUID(UUID uuid) {
        return skinTypeRepository.findById(uuid);
    }
}