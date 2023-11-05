package com.example.isa.dto.createUpdate;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
public class SkinTypeCreateUpdateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;

    public SkinTypeCreateUpdateDTO(String name) {
        this.name = name;
    }
}