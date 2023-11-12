package com.example.productManagement.controller;

import com.example.productManagement.dto.BrandCreateDTO;
import com.example.productManagement.dto.BrandReadDTO;
import com.example.productManagement.dto.ProductReadCollectionDTO;
import com.example.productManagement.entity.Brand;
import com.example.productManagement.entity.Product;
import com.example.productManagement.service.BrandService;
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
@RequestMapping("/api/product/brands")
@Validated
public class BrandController {

    private final BrandService brandService;

    @Autowired
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<BrandReadDTO> getAllBrands() {
        return brandService.getAllBrands()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public BrandReadDTO getBrandByName(@PathVariable String name) {
        Optional<Brand> brandOptional = brandService.findByName(name);
        return brandOptional.map(this::convertToDTO).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));
    }

    @GetMapping("/{name}/products")
    @ResponseStatus(HttpStatus.OK)
    public List<?> getProductsByBrand(@PathVariable String name) {
        Optional<List<Product>> productsOptional = brandService.getAllProductsFromBrand(name);

        return productsOptional.map(products -> {
            if (!products.isEmpty()) {
                return products.stream()
                        .map(this::convertToProductDTO)
                        .collect(Collectors.toList());
            } else {
                if(brandService.findByName(name).isEmpty()){
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
                }else{
                    return Collections.emptyList();
                }
            }
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));
    }



    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createBrand(@Valid @RequestBody BrandCreateDTO brand) {
        Optional<Brand> existingBrand = brandService.findByName(brand.getName());
        if (existingBrand.isPresent()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Brand already exists");
        }
        brandService.save(convertToEntity(brand));

    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void updateBrand(@PathVariable UUID uuid, @Valid @RequestBody BrandCreateDTO brand) {
        Optional<Brand> existingBrand = brandService.findByUUID(uuid);
        if (existingBrand.isPresent()) {
            Brand enitityBrand = convertToEntity(brand);
            enitityBrand.setUuid(uuid);
            brandService.save(enitityBrand);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
        }
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBrand(@PathVariable UUID uuid) {
        Optional<Brand> existingBrand = brandService.findByUUID(uuid);
        if (existingBrand.isPresent()) {
            brandService.deleteByUUID(uuid);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found");
        }
    }

    private BrandReadDTO convertToDTO(Brand brand) {
        return new BrandReadDTO(brand.getUuid(), brand.getName());
    }

    private Brand convertToEntity(BrandCreateDTO brand) {
        return new Brand(brand.getName());
    }
    private BrandCreateDTO convertToCreateUpdateDTO(Brand brand) {
        return new BrandCreateDTO(brand.getName());
    }
    private ProductReadCollectionDTO convertToProductDTO(Product product) {
        return new ProductReadCollectionDTO(product.getUuid(), product.getName(), product.getPrice());
    }

}
