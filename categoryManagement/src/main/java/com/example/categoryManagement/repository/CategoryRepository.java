package com.example.categoryManagement.repository;

import com.example.categoryManagement.entity.Category;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category> findByName(String name);
    Optional<Category> findByCode(Integer code);
    @Transactional
    void deleteById(UUID uuid);

}
