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

		Product faceWash = new Product("Face wash", 500, faceProducts, brand1, skinType1, 1000, "https://www.simpleskincare.com/sk-eu/content/dam/brands/simple/united_kingdom_ireland/56499587-5011451103870-01.jpg","Face wash for oily skin");
		productService.save(faceWash);
		Product faceCream = new Product("Face cream", 1000, faceProducts, brand1, skinType2, 500, "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRsWKxu11ZTpTXdRqvnRLyMpDy0NQdI1twfzg&usqp=CAU","Face cream for dehydrated skin");
		productService.save(faceCream);
		Product faceLotion = new Product("Face lotion", 800, faceProducts, brand2, skinType1, 200, "https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.harrys.com%2Fen%2Fus%2Fproducts%2Fdaily-face-lotion&psig=AOvVaw2BZXf_G1XT-lsQPqEf_NCa&ust=1699921536028000&source=images&cd=vfe&ved=0CBEQjRxqFwoTCIjc7avbv4IDFQAAAAAdAAAAABAI","Face lotion for oily skin");
		productService.save(faceLotion);
		Product bodyWash = new Product("Body wash", 200, bodyProducts, brand2, skinType2,130,"https://cdn11.bigcommerce.com/s-d7wmc7b75m/images/stencil/1280x1280/products/180/2310/1_18oz_Pure_Peace_Front_PLP2x__99144__35269__31279.1691508370.jpg?c=2", "Body wash for oily skin");
		productService.save(bodyWash);
		Product bodyCream = new Product("Body cream", 1050, bodyProducts, brand1, skinType2, 450, "https://fentybeauty.com/cdn/shop/files/64646_2000x.jpg?v=1689963948", "Body cream for dehydrated skin");
		productService.save(bodyCream);
		Product bodyLotion = new Product("Body lotion", 300, bodyProducts, brand2, skinType1, 50, "https://skinexpert.pl/wp-content/uploads/2018/11/MM-Advanced-Body-Lotion-700x840-DSC08680.jpg", "Body lotion for oily skin");
		productService.save(bodyLotion);
	}
}
