package com.example.isa.dto;


import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public class CategoryDTO implements Comparable<CategoryDTO>, Serializable {
    private String name;
    private int code;
    private List<String> products;
    @Override
    public int compareTo(CategoryDTO other){
        return Integer.compare(this.code, other.code);
    }
    @Override
    public String toString() {
        return "Name: " + this.name + ", Code: " + this.code+ ", Products: " + this.products;
    }
}
