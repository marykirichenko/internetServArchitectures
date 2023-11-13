package com.example.productManagement.repository;
        import com.example.productManagement.entity.Brand;
        import com.example.productManagement.entity.Category;
        import com.example.productManagement.entity.Product;
        import com.example.productManagement.entity.SkinType;
        import jakarta.transaction.Transactional;
        import org.springframework.data.jpa.repository.*;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;

        import java.util.List;
        import java.util.Optional;
        import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID>{
    Optional<Product> findByName(String name);
    List<Product> findByPrice(int price);
    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName")
    Optional<List<Product>> findByCategoryName(@Param("categoryName") String categoryName);

    @Query("SELECT p FROM Product p WHERE p.brand.name = :brandName")
    Optional<List<Product>> findByBrandName(@Param("brandName") String brandName);


    @Query("SELECT p FROM Product p WHERE p.skinType.name = :skinTypeName")
    Optional<List<Product>> findBySkinTypeName(@Param("skinTypeName") String skinTypeName);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM Product p " +
            "WHERE p.name = :name AND p.category = :category AND p.brand = :brand AND p.skinType = :skinType AND p.description = :description AND p.quantity = :quantity AND p.price = :price")
    boolean existsByNameAndCategoryAndBrandAndSkinType(
            @Param("name") String name,
            @Param("category")Category category,
            @Param("brand") Brand brand,
            @Param("skinType") SkinType skinType,
            @Param("description") String description,
            @Param("quantity") int quantity,
            @Param("price") int price
    );

    @Transactional
    void deleteById(UUID uuid);

}
