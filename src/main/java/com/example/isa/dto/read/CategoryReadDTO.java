package com.example.isa.dto.read;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CategoryReadDTO {
    private UUID uuid;
    private String name;
    private int code;

    public CategoryReadDTO(UUID uuid, String name, int code) {
        this.uuid = uuid;
        this.name = name;
        this.code = code;
    }
}

