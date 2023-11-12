package com.example.productManagement.repository;

import com.example.productManagement.entity.Category;
import com.example.productManagement.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);
    @Transactional
    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.category.name = :name")
    Optional<List<Product>> findProductsByCategory(@Param("name") String name);
    @Transactional
    void deleteById(UUID uuid);

    @Transactional
    void deleteByName(String name);

}
