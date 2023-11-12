package com.example.productManagement.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.UUID;
@EqualsAndHashCode
@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "product")
public class Product implements Comparable<Product>, Serializable {
    @Id
    private UUID uuid = UUID.randomUUID();
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "category_id", nullable = false)
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name= "brand_id", nullable = false)
    private Brand brand;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="skin_type_id", nullable = false)
    private SkinType skinType;

    public Product(String name, int price, Category category, Brand brand, SkinType skinType){
        this.name = name;
        this.price = price;
        this.category = category;
        this.brand = brand;
        this.skinType = skinType;
    }


    @Override
    public int compareTo(Product other){
        return Integer.compare(this.price, other.price);
    }
    @Override
    public String toString(){
        return "UUID "+ uuid+ "Name: " + this.name + "Category Id"+ this.category.getUuid()+" Price: " + this.price +  " Brand: " + this.brand + " Skin type: " + this.skinType;
    }
}
