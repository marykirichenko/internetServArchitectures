package com.example.isa.dto.read;

import java.util.UUID;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProductReadDTO {
    private UUID uuid;
    private String name;
    private int price;
    private String categoryName;
    private String brandName;
    private String skinTypeName;

    public ProductReadDTO(UUID uuid, String name, int price, String categoryName, String brandName, String skinTypeName) {
        this.uuid = uuid;
        this.name = name;
        this.price = price;
        this.categoryName = categoryName;
        this.brandName = brandName;
        this.skinTypeName = skinTypeName;
    }

}
