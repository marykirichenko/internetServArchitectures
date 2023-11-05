package com.example.isa.additionalTasks;
import com.example.isa.entity.Category;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class CustomThreadPoolTask {
    private List<Category> categories;
    private int threads;
    public CustomThreadPoolTask(List<Category> categories, int threads ){
        this.categories = categories;
        this.threads = threads;
    }
    public void runTaskWithPool(){
        ForkJoinPool pool = new ForkJoinPool(threads);
        try{
            categories.parallelStream().forEach(category -> {
                pool.submit(() -> {
                    category.getProducts().parallelStream().forEach(product -> {
                        System.out.println(product.toString() + " thread " + Thread.currentThread().getName());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    });
                }).join();
            });
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            pool.shutdown();
        }
    }

}
