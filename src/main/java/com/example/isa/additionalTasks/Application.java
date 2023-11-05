package com.example.isa.additionalTasks;


import com.example.isa.dto.ProductDTO;
import com.example.isa.entity.Brand;
import com.example.isa.entity.Category;
import com.example.isa.entity.Product;
import com.example.isa.entity.SkinType;
import org.springframework.stereotype.Component;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class Application {
    private final List<Category> categoriesList;
    public Application(List<Category> categoriesList){
        this.categoriesList = categoriesList;
    }

    public void initializeData() {
        Category faceProducts = new Category("Face Products", 100);
        Category bodyProducts = new Category("Body Products", 200);

        Brand brand1 = new Brand("Brand1");
        Brand brand2 = new Brand("Brand2");

        SkinType skinType1 = new SkinType("DEHYDRATED");
        SkinType skinType2 = new SkinType("OILY");

        Product faceWash = new Product("Face wash", 500, faceProducts, brand1, skinType1);
        faceProducts.addProduct(faceWash);
        Product faceCream = new Product("Face cream", 1000, faceProducts, brand1, skinType2);
        faceProducts.addProduct(faceCream);
        Product faceLotion = new Product("Face lotion", 800, faceProducts, brand2, skinType1);
        faceProducts.addProduct(faceLotion);

        Product bodyWash = new Product("Body wash", 200, bodyProducts, brand2, skinType2);
        bodyProducts.addProduct(bodyWash);
        Product bodyCream = new Product("Body cream", 1050, bodyProducts, brand1, skinType2);
        bodyProducts.addProduct(bodyCream);
        Product bodyLotion = new Product("Body lotion", 300, bodyProducts, brand2, skinType1);
        bodyProducts.addProduct(bodyLotion);

        categoriesList.add(faceProducts);
        categoriesList.add(bodyProducts);
    }


    public void printCategoriesAndElements() {
        categoriesList.forEach(System.out::println);
    }
    public void printAllProducts() {
        Set<Product> charactersStream = categoriesList.stream()
                .map(Category::getProducts)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        charactersStream.forEach(System.out::println);
    }

    public List<Product> filterProducts(){
        return categoriesList.stream()
                .map(Category::getProducts)
                .flatMap(Collection::stream)
                .filter(product -> product.getName().startsWith("Face"))
                .sorted()
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public List<ProductDTO> DTOProducts(List<Product> productsList){
        System.out.println("DTO");
        return productsList.stream()
                .map(product -> ProductDTO.builder()
                        .name(product.getName())
                        .price(product.getPrice())
                        .category(product.getCategory().getName())
                        .build())
                .peek(System.out::println)
                .collect(Collectors.toList());
    }

    public void serializeProducts(List<Product> productsList) throws IOException, ClassNotFoundException {
        System.out.println("Serialize");
        FileOutputStream fileOutputStream = new FileOutputStream("lab1.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        productsList.forEach(product -> {
            try {
                objectOutputStream.writeObject(product);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        objectOutputStream.close();
        fileOutputStream.close();
        FileInputStream fileInputStream = new FileInputStream("lab1.ser");
        List<Product> productsRead = new ArrayList<>();

        try (fileInputStream; ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (true) {
                Product product = (Product) objectInputStream.readObject();
                productsRead.add(product);
            }
        } catch (EOFException e) {
            System.out.println("End of file");
            productsRead.forEach(System.out::println);
        }
    }

    public void threadPoolTask(){
        System.out.println("Thread pool task");
        int[] threads = {1, 2, 5};
        for(int thread : threads){
            System.out.println("size: " + thread);
            CustomThreadPoolTask customThreadPoolTask = new CustomThreadPoolTask(categoriesList, thread);
            customThreadPoolTask.runTaskWithPool();
        }
    }

}