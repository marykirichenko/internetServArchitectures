package com.example.isa.repository;

import com.example.isa.entity.Brand;
import com.example.isa.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {
    @Transactional
    void deleteById(UUID uuid);
    @Transactional
    @Query("SELECT p FROM Product p JOIN FETCH p.brand WHERE p.brand.name = :name")
    Optional<List<Product>> findProductsByBrandName(@Param("name") String name);


    Optional<Brand> findByName(String name);


}
