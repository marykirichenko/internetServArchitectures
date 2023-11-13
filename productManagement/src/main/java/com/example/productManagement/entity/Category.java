package com.example.productManagement.entity;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@Entity
@Table(name="category")
public class Category implements Serializable {
    @Id
    private UUID uuid = UUID.randomUUID();
    @JsonProperty("name")
    @Column(nullable = false)
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
    public Category() {
        this.products = new ArrayList<>();
    }
    public Category(String name) {
        this.name = name;
        this.products = new ArrayList<>();
    }
    @Override
    public String toString() {
        return "Category{" +
                "uuid=" + uuid +
                ", name='" + name + '\'' +
                '}';
    }

}
