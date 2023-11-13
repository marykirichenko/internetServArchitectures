package com.example.productManagement.controller;

import com.example.productManagement.dto.ProductCreateDTO;
import com.example.productManagement.dto.ProductReadDTO;
import com.example.productManagement.entity.Brand;
import com.example.productManagement.entity.Category;
import com.example.productManagement.entity.Product;
import com.example.productManagement.entity.SkinType;
import com.example.productManagement.service.BrandService;
import com.example.productManagement.service.CategoryService;
import com.example.productManagement.service.ProductService;
import com.example.productManagement.service.SkinTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/product/products")
public class ProductController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;
    private final SkinTypeService skinTypeService;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService,  BrandService brandService, SkinTypeService skinTypeService) {
        this.productService = productService;
        this.brandService = brandService;
        this.skinTypeService = skinTypeService;
        this.categoryService = categoryService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductReadDTO> getAllProducts(){
        return productService.getAllProducts().stream().map(this::convertToProductReadDTO).toList();
    }

    @PostMapping("/categoryNotifications")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<String> handleNotificationFromCategoryController(@RequestParam MultiValueMap<String, String> notification) {
        String categoryName = notification.getFirst("categoryName");
        String action = notification.getFirst("action");
        System.out.println("Received notification: Category " + categoryName + " - Action: " + action);


        System.out.println("Received notification: Category " + categoryName + " - Action: " + action);
        if(action != null && action.equals("delete")){
            categoryService.deleteByName(categoryName);
        }
        if(action != null && action.equals("create")){
            categoryService.save(new Category(categoryName));
        }
        return new ResponseEntity<>("Notification received", HttpStatus.OK);
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
    public void createProduct(@Valid  @RequestBody ProductCreateDTO product){
        Category category = categoryService.findByName(product.getCategoryName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));
        Brand brand = brandService.findByName(product.getBrandName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Brand not found"));
        SkinType skinType = skinTypeService.findByName(product.getSkinTypeName())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Skin type not found"));

        if (productService.isProductUnique(product.getName(), category, brand, skinType, product.getDescription(), product.getQuantity(), product.getPrice())) {
            productService.save(convertToEntity(product));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product already exists");
        }
    }

    @PutMapping("/{uuid}")
    @ResponseStatus(HttpStatus.OK)
    public void updateProduct(@Valid  @RequestBody ProductCreateDTO product, @PathVariable UUID uuid){
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

    private Product convertToEntity(ProductCreateDTO product){
        Category category = categoryService.findByName(product.getCategoryName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Category not found"));
        Brand brand = brandService.findByName(product.getBrandName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Brand not found"));
        SkinType skinType = skinTypeService.findByName(product.getSkinTypeName()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Skin type not found"));
        return new Product(product.getName(), product.getPrice(), category, brand, skinType, product.getQuantity(), product.getImgLink(), product.getDescription());
    }
}
