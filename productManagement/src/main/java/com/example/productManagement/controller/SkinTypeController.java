package com.example.productManagement.controller;


import com.example.productManagement.dto.ProductReadCollectionDTO;
import com.example.productManagement.dto.SkinTypeCreateDTO;
import com.example.productManagement.dto.SkinTypeReadDTO;
import com.example.productManagement.entity.Product;
import com.example.productManagement.entity.SkinType;
import com.example.productManagement.service.SkinTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/api/product/skinTypes")
public class SkinTypeController {
    private final SkinTypeService skinTypeService;

    @Autowired
    public SkinTypeController(SkinTypeService skinTypeService){
        this.skinTypeService = skinTypeService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<SkinTypeReadDTO> getAllSkinTypes(){
        return skinTypeService.getAllSkinTypes().stream().map(this::convertToReadDTO).collect(Collectors.toList());
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{name}/products")
    public List<ProductReadCollectionDTO> getProductsFromSkinType(@PathVariable String name){
        Optional<List<Product>> optionalProducts = skinTypeService.getAllProductsBySkinType(name);
        if(optionalProducts.isPresent() && skinTypeService.findByName(name).isPresent()){
            return optionalProducts.get().stream().map(this::convertToProductReadCollectionDTO).collect(Collectors.toList());
        }else{
            if(skinTypeService.findByName(name).isPresent()) {
                return Collections.emptyList();
            }else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin type not found");
            }
        }
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{name}")
    public SkinTypeReadDTO getSkinTypeByName(@PathVariable  String name){
        Optional<SkinType> optionalSkinType = skinTypeService.findByName(name);
        if(optionalSkinType.isPresent()){
            return convertToReadDTO(optionalSkinType.get());
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin type not found");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createSkinType(@Valid @RequestBody SkinTypeCreateDTO skinTypeCreateUpdateDTO){
        Optional<SkinType> optionalSkinType = skinTypeService.findByName(skinTypeCreateUpdateDTO.getName());
        if(optionalSkinType.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Skin type already exists");
        }else{
            SkinType skinTypeEntity = convertToEntity(skinTypeCreateUpdateDTO);
            skinTypeService.save(skinTypeEntity);

        }
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{uuid}")
    public void updateSkinType(@PathVariable UUID uuid, @RequestBody SkinTypeCreateDTO skinTypeCreateDTO){
        Optional<SkinType> optionalSkinType = skinTypeService.findByUUID(uuid);
        if(optionalSkinType.isPresent()){
            SkinType skinType = convertToEntity(skinTypeCreateDTO);
            skinType.setUuid(uuid);
            skinTypeService.save(skinType);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin type not found");
        }
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{uuid}")
    public void deleteSkinType(@Valid @PathVariable UUID uuid){
        Optional<SkinType> optionalSkinType = skinTypeService.findByUUID(uuid);
        if(optionalSkinType.isPresent()){
            skinTypeService.deleteByUUID(uuid);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin type not found");
        }
    }

    private SkinTypeReadDTO convertToReadDTO(SkinType skinType){
        return new SkinTypeReadDTO(skinType.getUuid(), skinType.getName());
    }

    private SkinType convertToEntity(SkinTypeCreateDTO skinTypeCreateUpdateDTO){
        return new SkinType(skinTypeCreateUpdateDTO.getName());
    }
    private ProductReadCollectionDTO convertToProductReadCollectionDTO(Product product) {
        return new ProductReadCollectionDTO(product.getUuid(), product.getName(), product.getPrice());
    }

}
