package com.example.productManagement.dto;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SkinTypeReadDTO {
    private UUID uuid;
    private String name;

    public SkinTypeReadDTO(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
    }

}

