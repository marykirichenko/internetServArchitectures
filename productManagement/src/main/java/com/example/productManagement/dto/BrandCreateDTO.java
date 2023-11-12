package com.example.productManagement.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
public class BrandCreateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    public BrandCreateDTO(String name) {
        this.name = name;
    }
}
