package com.example.isa;

import com.example.isa.additionalTasks.Application;
import com.example.isa.entity.Product;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class IsaApplication{

	public static void main(String[] args) {

		ConfigurableApplicationContext context = SpringApplication.run(IsaApplication.class, args);
//		Application application = context.getBean(Application.class);
//		application.initializeData();
//		application.printCategoriesAndElements();
//		application.printAllProducts();
//		List<Product> products = application.filterProducts();
//		application.DTOProducts(products);
//		//application.serializeProducts(products);
//		application.threadPoolTask();
	}

}
