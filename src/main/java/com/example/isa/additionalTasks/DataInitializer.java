package com.example.isa.additionalTasks;

import com.example.isa.entity.Brand;
import com.example.isa.entity.Category;
import com.example.isa.entity.Product;
import com.example.isa.entity.SkinType;
import com.example.isa.service.BrandService;
import com.example.isa.service.CategoryService;
import com.example.isa.service.ProductService;
import com.example.isa.service.SkinTypeService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class DataInitializer implements CommandLineRunner {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final BrandService brandService;

    private final SkinTypeService skinTypeService;

    private final Scanner scanner;

    @Autowired
    public DataInitializer(ProductService productService, CategoryService categoryService, BrandService brandService, SkinTypeService skinTypeService){
        this.productService = productService;
        this.categoryService = categoryService;
        this.brandService = brandService;
        this.skinTypeService = skinTypeService;
        this.scanner = new Scanner(System.in);
    }

    @Override
    public void run(String... args) {
        Category faceProducts = new Category("Face Products", 100);
        Category bodyProducts = new Category("Body Products", 200);
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

        Boolean exit = false;
        displayCommands();
        while (!exit) {
            System.out.println("Enter command:");
            String command = scanner.nextLine();
            switch (command.toLowerCase()) {
                case "help":
                    displayCommands();
                    break;
                case "list-categories":
                    listCategories();
                    break;
                case "list-products":
                    listProducts();
                    break;
                case "list-skintypes":
                    listSkinTypes();
                    break;
                case "list-brands":
                    listBrands();
                    break;
                case "add-category":
                    addCategory();
                    break;
                case "add-product":
                    addProduct();
                    break;
                case "add-skintype":
                    addSkinType();
                    break;
                case "add-brand":
                    addBrand();
                    break;
                case "delete-category":
                    deleteCategory();
                    break;
                case "delete-product":
                    deleteProduct();
                    break;
                case "delete-skintype":
                    deleteSkinType();
                    break;
                case "delete-brand":

                    deleteBrand();
                    break;
                case "get-products-by-brand":
                    getProductsByBrand();
                    break;
                case "exit":
                    System.out.println("Exiting the application.");
                    exit = true;
                    System.exit(0);
                default:
                    System.out.println("Invalid command. Type 'help' to see available commands.");
            }
        }
        scanner.close();
    }

    private void displayCommands() {
        System.out.println("Available Commands:");
        System.out.println("help - Display available commands");
        System.out.println("list-categories - List all categories");
        System.out.println("list-products - List all products");
        System.out.println("list-skintypes - List all skin types");
        System.out.println("list-brands - List all brands");
        System.out.println("add-category - Add new category");
        System.out.println("add-product - Add new product");
        System.out.println("add-skintype - Add new skin type");
        System.out.println("add-brand - Add new brand");
        System.out.println("delete-category - Delete an existing category");
        System.out.println("delete-product - Delete an existing product");
        System.out.println("delete-skintype - Delete an existing skin type");
        System.out.println("delete-brand - Delete an existing brand");

        System.out.println("get-products-by-brand");
        System.out.println("exit - Exit the application");
    }

    private void getProductsByBrand(){
        System.out.println("Enter brand name");
        String brandName = scanner.nextLine();
        Optional<Brand> brand = brandService.findByName(brandName);
        if(brand.isEmpty()){
            System.out.println("Brand not found");
        } else {
            List<Product> list = brandService.getAllProductsFromBrand(brand.get().getName()).orElseThrow(() -> new EntityNotFoundException("No brand was not found"));
            list.forEach(product -> System.out.println(product.getName()));
        }
    }
    private void listCategories() {
        System.out.println("List of Categories:");
        List<Category> categories = categoryService.getAllCategories();
        categories.forEach(category -> System.out.println(category.getName()));
    }

    private void listProducts() {
        System.out.println("List of Products:");
        List<Product> elements = productService.getAllProducts();
        elements.forEach(element -> System.out.println(element.getName()));
    }

    private void listSkinTypes() {
        System.out.println("List of Skintypes:");
        List<SkinType> elements = skinTypeService.getAllSkinTypes();
        elements.forEach(element -> System.out.println(element.getName()));
    }

    private void listBrands() {
        System.out.println("List of Brands:");
        List<Brand> elements = brandService.getAllBrands();
        elements.forEach(element -> System.out.println(element.getName()));
    }

    private void addCategory() {
        System.out.println("Enter category name:");
        String categoryName = scanner.nextLine();


        System.out.println("Enter category code:");
        int categoryCode = scanner.nextInt();
        scanner.nextLine();
        Category category = new Category(categoryName, categoryCode);
        categoryService.save(category);

        System.out.println("Category added successfully.");
    }

    private void addSkinType() {
        System.out.println("Enter skintype:");
        String skintypeName = scanner.nextLine();
        SkinType skintype = new SkinType(skintypeName);
        skinTypeService.save(skintype);
        System.out.println("Skintype added successfully.");
    }

    private void addBrand() {
        System.out.println("Enter brand name:");
        String brandName = scanner.nextLine();
        Brand brand = new Brand(brandName);
        brandService.save(brand);
        System.out.println("Brand added successfully.");
    }

    private void addProduct() {
        System.out.println("Enter product name:");
        String productName = scanner.nextLine();

        System.out.println("Enter product price:");
        int productPrice = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter category name:");
        String categoryName = scanner.nextLine();

        Category category = categoryService.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        System.out.println("Enter brand name:");
        String brandName = scanner.nextLine();

        Brand brand = brandService.findByName(brandName)
                .orElseThrow(() -> new RuntimeException("Brand not found"));

        System.out.println("Enter skin type:");
        String skinTypeName = scanner.nextLine();

        SkinType skinType = skinTypeService.findByName(skinTypeName)
                .orElseThrow(() -> new RuntimeException("Skin type not found"));

        Product product = new Product(productName, productPrice, category, brand, skinType);
        productService.save(product);

        System.out.println("Element added successfully.");
    }

    private void deleteCategory() {
        System.out.println("Enter category name to delete:");
        String categoryName = scanner.nextLine();

        Category category = categoryService.findByName(categoryName)
                .orElseThrow(() -> new RuntimeException("Element not found"));

        categoryService.deleteByUUID(category.getUuid());

        System.out.println("Category" + categoryName +" deleted successfully.");
    }

    private void deleteProduct() {
        System.out.println("Enter product name to delete:");
        String productName = scanner.nextLine();

        Product product = productService.findByName(productName).orElseThrow(() -> new RuntimeException("Product not found"));

        productService.deleteByUUID(product.getUuid());

        System.out.println("Product" + productName +" deleted successfully.");
    }

    private void deleteSkinType() {
        System.out.println("Enter skintype name to delete:");
        String skintypeName = scanner.nextLine();

        SkinType skinType= skinTypeService.findByName(skintypeName).orElseThrow(() -> new RuntimeException("Skintype not found"));

        skinTypeService.deleteByUUID(skinType.getUuid());

        System.out.println("Skin type" + skintypeName +" deleted successfully.");
    }

    private void deleteBrand() {
        System.out.println("Enter brand name to delete:");
        String brandName = scanner.nextLine();

        Brand brand = brandService.findByName(brandName).orElseThrow(() -> new RuntimeException("Brand not found"));

        brandService.deleteByUUID(brand.getUuid());

        System.out.println("Brand" + brandName +" deleted successfully.");
    }

}

