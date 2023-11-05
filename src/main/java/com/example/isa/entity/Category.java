package com.example.isa.entity;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
@EqualsAndHashCode(exclude = "products")
@Getter
@Setter
@Entity
@Table(name="category")
public class Category implements Comparable<Category>, Serializable {
    @Id
    private UUID uuid = UUID.randomUUID();
    @JsonProperty("name")
    @Column(nullable = false)
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @JsonProperty("code")
    @Min(value = 1, message = "Code must be greater than 0")
    @Column(nullable = false)
    private int code;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
    public Category() {
        this.products = new ArrayList<>();
    }
    public Category(String name, int code) {
        this.name = name;
        this.code = code;
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    @Override
    public String toString(){
        return  "UUID "+ uuid+ "Name: " + this.name + " Code: " + this.code + " Products: " + this.products;
    }
    @Override
    public int compareTo(Category other){
        return Integer.compare(this.code, other.code);
    }
}
