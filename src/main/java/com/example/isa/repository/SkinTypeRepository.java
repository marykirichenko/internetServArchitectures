package com.example.isa.repository;

import com.example.isa.entity.Product;
import com.example.isa.entity.SkinType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SkinTypeRepository extends JpaRepository<SkinType, UUID> {
    @Transactional
    void deleteById(UUID uuid);

    Optional<SkinType> findByName(String name);

    @Transactional
    @Query("SELECT p FROM Product p JOIN FETCH p.skinType WHERE p.skinType.name = :name")
    Optional<List<Product>> findProductsBySkinType(@Param("name") String name);

}
