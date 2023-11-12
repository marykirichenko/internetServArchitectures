package com.example.productManagement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProductCreateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value=1, message = "Price cannot be blank")
    @JsonProperty("price")
    private int price;
    @NotBlank(message = "Category name cannot be blank")
    private String categoryName;
    @NotBlank(message = "Brand name cannot be blank")
    private String brandName;
    @NotBlank(message = "Skin type name cannot be blank")
    private String skinTypeName;

    public ProductCreateDTO(String name, int price, String categoryName, String brandName, String skinTypeName) {
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.skinTypeName = skinTypeName;
    }
}