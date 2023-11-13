package com.example.productManagement.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
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

    @Min(value = 1, message = "Code must be greater than 0")
    @Column(nullable = false)
    private int quantity = 0;
    @Column(nullable = false)
    private String imgLink;
    @Column(nullable = false)
    private String description;

    public Product(String name, int price, Category category, Brand brand, SkinType skinType,int quantity, String imgLink, String description){
        this.name = name;
        this.price = price;
        this.category = category;
        this.brand = brand;
        this.skinType = skinType;
        this.imgLink = imgLink;
        this.description = description;
        this.quantity = quantity;
    }


    @Override
    public int compareTo(Product other){
        return Integer.compare(this.price, other.price);
    }
    @Override
    public String toString(){
        return "UUID "+ uuid+ "Name: " + this.name + "Category Id"+ this.category.getUuid()+" Price: " + this.price +  " Brand: " + this.brand + " Skin type: " + this.skinType + " Quantity: " + this.quantity + " Image link: " + this.imgLink + " Description: " + this.description;
    }
}
