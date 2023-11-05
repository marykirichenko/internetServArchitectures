package com.example.isa.dto.readCollection;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor

public class ProductReadCollectionDTO {
    private UUID uuid;
    private String name;
    private int price;

    public ProductReadCollectionDTO(UUID uuid, String name, int price) {
        this.uuid = uuid;
        this.name = name;
        this.price = price;
    }
}
