package com.example.isa.controller;

import com.example.isa.dto.createUpdate.ProductCreateUpdateDTO;
import com.example.isa.dto.read.ProductReadDTO;
import com.example.isa.entity.Brand;
import com.example.isa.entity.Category;
import com.example.isa.entity.Product;
import com.example.isa.entity.SkinType;
import com.example.isa.service.BrandService;
import com.example.isa.service.CategoryService;
import com.example.isa.service.ProductService;
import com.example.isa.service.SkinTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SkinTypeService skinTypeService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService, BrandService brandService, SkinTypeService skinTypeService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.skinTypeService = skinTypeService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductReadDTO> getAllProducts(){
        return productService.getAllProducts().stream().map(this::convertToProductReadDTO).toList();
    }

    @GetMapping("/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductReadDTO> getProductByName(@PathVariable String name){
        if(productService.findByName(name).isPresent()){
            return productService.findByName(name).stream().map(this::convertToProductReadDTO).toList();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product not found");
        }
    }

    @GetMapping("/price/{price}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductReadDTO> getProductByPrice(@PathVariable int price){
       return productService.findByPrice(price).stream().map(this::convertToProductReadDTO).toList();
    }

    @GetMapping("/category/{categoryName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductReadDTO> getProductByCategoryName(@PathVariable String categoryName){
        if(productService.findByCategoryName(categoryName).isPresent() && productService.findByCategoryName(categoryName).get().size() > 0){
            return productService.findByCategoryName(categoryName).stream().flatMap(productsList -> productsList.stream().map(this::convertToProductReadDTO)).toList();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found");
        }
    }

    @GetMapping("/brand/{brandName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductReadDTO> getProductByBrandName(@PathVariable String brandName){
        if(productService.findByBrandName(brandName).isPresent() && productService.findByBrandName(brandName).get().size() > 0){
            return productService.findByBrandName(brandName).stream().flatMap(productsList -> productsList.stream().map(this::convertToProductReadDTO)).toList();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Brand not found");
        }
    }

    @GetMapping("/skinType/{skinTypeName}")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductReadDTO> getProductBySkinTypeName(@PathVariable String skinTypeName){
        if(productService.findBySkinTypeName(skinTypeName).isPresent() && productService.findBySkinTypeName(skinTypeName).get().size() > 0){
            return productService.findBySkinTypeName(skinTypeName).stream().flatMap(productsList -> productsList.stream().map(this::convertToProductReadDTO)).toList();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Skin type not found");
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@Valid  @RequestBody ProductCreateUpdateDTO product){
        Category category = categoryService.findByName(product.getCategoryName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        Brand brand = brandService.findByName(product.getBrandName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));
        SkinType skinType = skinTypeService.findByName(product.getSkinTypeName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin type not found"));

        if (productService.isProductUnique(product.getName(), category, brand, skinType)) {
            productService.save(convertToEntity(product));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already exists");
        }
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@Valid  @RequestBody ProductCreateUpdateDTO product, @PathVariable UUID uuid){
        Optional<Product> existingProduct = productService.findByUUID(uuid);
        if(!existingProduct.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product do not exist");
        }
        Product entityToSave = convertToEntity(product);
        entityToSave.setUuid(uuid);
        productService.save(entityToSave);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProduct(@PathVariable UUID uuid){
        Optional<Product> existingProduct = productService.findByUUID(uuid);
        if(!existingProduct.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Product do not exist");
        }
        productService.deleteByUUID(uuid);
    }

    private ProductReadDTO convertToProductReadDTO(Product product){
        return new ProductReadDTO(product.getUuid(), product.getName(), product.getPrice(), product.getCategory().getName(), product.getBrand().getName(), product.getSkinType().getName());
    }

    private Product convertToEntity(ProductCreateUpdateDTO product){
        Category category = categoryService.findByName(product.getCategoryName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));
        Brand brand = brandService.findByName(product.getBrandName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Brand not found"));
        SkinType skinType = skinTypeService.findByName(product.getSkinTypeName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Skin type not found"));
        return new Product(product.getName(), product.getPrice(), category, brand, skinType);
    }
}
