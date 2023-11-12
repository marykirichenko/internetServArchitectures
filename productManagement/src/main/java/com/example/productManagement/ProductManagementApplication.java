package com.example.productManagement;

import com.example.productManagement.entity.Brand;
import com.example.productManagement.entity.Category;
import com.example.productManagement.entity.Product;
import com.example.productManagement.entity.SkinType;
import com.example.productManagement.service.BrandService;
import com.example.productManagement.service.CategoryService;
import com.example.productManagement.service.ProductService;
import com.example.productManagement.service.SkinTypeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ProductManagementApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(ProductManagementApplication.class, args);
		ProductService productService = context.getBean(ProductService.class);
		BrandService brandService = context.getBean(BrandService.class);
		SkinTypeService skinTypeService = context.getBean(SkinTypeService.class);
		CategoryService categoryService = context.getBean(CategoryService.class);

		Category faceProducts = new Category("Face Products");
		Category bodyProducts = new Category("Body Products");


		categoryService.save(faceProducts);
		categoryService.save(bodyProducts);

		Brand brand1 = new Brand("Brand1");
		Brand brand2 = new Brand("Brand2");
		brandService.save(brand1);
		brandService.save(brand2);

		SkinType skinType1 = new SkinType("DEHYDRATED");
		SkinType skinType2 = new SkinType("OILY");
		skinTypeService.save(skinType1);
		skinTypeService.save(skinType2);

		Product faceWash = new Product("Face wash", 500, faceProducts, brand1, skinType1);
		productService.save(faceWash);
		Product faceCream = new Product("Face cream", 1000, faceProducts, brand1, skinType2);
		productService.save(faceCream);
		Product faceLotion = new Product("Face lotion", 800, faceProducts, brand2, skinType1);
		productService.save(faceLotion);
		Product bodyWash = new Product("Body wash", 200, bodyProducts, brand2, skinType2);
		productService.save(bodyWash);
		Product bodyCream = new Product("Body cream", 1050, bodyProducts, brand1, skinType2);
		productService.save(bodyCream);
		Product bodyLotion = new Product("Body lotion", 300, bodyProducts, brand2, skinType1);
		productService.save(bodyLotion);
	}
}
