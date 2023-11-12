package com.example.categoryManagement;

import com.example.categoryManagement.entity.Category;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.example.categoryManagement.service.CategoryService;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class CategoryManagementApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context  = SpringApplication.run(CategoryManagementApplication.class, args);
		CategoryService categoryService = context.getBean(CategoryService.class);
		Category faceProducts = new Category("Face Products", 100);
		Category bodyProducts = new Category("Body Products", 200);
		categoryService.save(faceProducts);
		categoryService.save(bodyProducts);
	}

	@Bean
	public RestTemplate restTemplate(){
		return new RestTemplate();
	}

}
