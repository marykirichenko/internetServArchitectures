package com.example.categoryManagement.entity;

import java.io.Serializable;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
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
    public Category() {

    }
    public Category(String name, int code) {
        this.name = name;
        this.code = code;
    }

    @Override
    public String toString(){
        return  "UUID "+ uuid+ "Name: " + this.name + " Code: " + this.code ;
    }
    @Override
    public int compareTo(Category other){
        return Integer.compare(this.code, other.code);
    }
}
