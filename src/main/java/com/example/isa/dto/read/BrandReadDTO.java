package com.example.isa.dto.read;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class BrandReadDTO {
    private UUID uuid;
    private String name;

    public BrandReadDTO(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }
}
