package com.example.isa.dto.createUpdate;

import com.example.isa.dto.read.CategoryReadDTO;
import com.example.isa.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCreateUpdateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value = 1, message = "Code must be greater than 0")
    private int code;


    public CategoryCreateUpdateDTO(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public CategoryReadDTO convertToReadDTO(Category category){
        return new CategoryReadDTO(category.getUuid(), category.getName(), category.getCode());
    }

}

