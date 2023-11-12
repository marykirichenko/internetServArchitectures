package com.example.productManagement.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name="brand")
@Entity
public class Brand implements Comparable<Brand>{
    @Id
    private UUID uuid = UUID.randomUUID();

    @Column(nullable = false)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> product;

    public Brand(String name){
        this.name = name;
    }

    @Override
    public int compareTo(Brand other){
        return this.name.compareTo(other.name);
    }

    @Override
    public String toString(){
        return "Name: "+ this.name;
    }

}
