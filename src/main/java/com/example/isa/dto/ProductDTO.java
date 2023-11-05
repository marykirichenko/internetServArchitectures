package com.example.isa.dto;


import lombok.Builder;

import java.io.Serializable;

@Builder
public class ProductDTO implements Comparable<ProductDTO>, Serializable {
    private String name;
    private int price;
    private String category;
    @Override
    public int compareTo(ProductDTO other){
        return Integer.compare(this.price, other.price);
    }
    @Override
    public String toString() {
        return "Name: " + this.name + ", Price: " + this.price;
    }
}
