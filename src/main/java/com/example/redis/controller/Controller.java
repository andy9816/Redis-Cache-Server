package com.example.redis.controller;

import com.example.redis.entity.Product;
import com.example.redis.repository.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@EnableCaching
public class Controller {
    @Autowired
    ProductDao dao;
    @PostMapping
    public Product save(@RequestBody Product product){
        return dao.save(product);
    }
    @GetMapping
    public List<Product> getAllProduct(){
        return dao.findAll();
    }
    @GetMapping("/{id}")
    @Cacheable(key = "#id", value = "Product", unless = "#result.price > 99")
    public Product findProduct(@PathVariable int id){
        return dao.findProductById(id);
    }
    @DeleteMapping("/{id}")
    @CacheEvict(key = "#id", value = "Product")
    public String remove(@PathVariable int id){
        return dao.deleteProduct(id);
    }

}
