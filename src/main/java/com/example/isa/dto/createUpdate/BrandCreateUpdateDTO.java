package com.example.isa.dto.createUpdate;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
public class BrandCreateUpdateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    public BrandCreateUpdateDTO(String name) {
        this.name = name;
    }
}
