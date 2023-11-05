package com.example.isa.entity;

import com.example.isa.dto.ProductDTO;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@Table(name = "skin_type")
@Entity
@NoArgsConstructor
public class SkinType {
    @Id
    private UUID uuid = UUID.randomUUID();

    @JsonProperty("name")
    @Column(nullable = false)
    @NotBlank(message = "Name cannot be blank")
    private String name;

    public SkinType(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "skinType", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> products;

    @Override
    public String toString() {
        return "Type " + this.name;
    }
}
