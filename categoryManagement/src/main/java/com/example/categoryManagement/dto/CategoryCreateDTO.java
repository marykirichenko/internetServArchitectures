package com.example.categoryManagement.dto;

import com.example.categoryManagement.entity.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryCreateDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Min(value = 1, message = "Code must be greater than 0")
    private int code;


    public CategoryCreateDTO(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public CategoryReadDTO convertToReadDTO(Category category){
        return new CategoryReadDTO(category.getUuid(), category.getName(), category.getCode());
    }

}

