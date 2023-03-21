package com.example.effectivemobileentertask.Controller;

import com.example.effectivemobileentertask.Entity.Product;
import com.example.effectivemobileentertask.Repository.ProductsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/admin")
public class ProductController {
    @Autowired
    private ProductsRepo productsRepo;

    @PostMapping("/addNewProduct")
    public String addNewProduct(@RequestBody Product product){
        productsRepo.save(product);
        return "Product have been saved ...";
    }

    @GetMapping("/getListOfProducts")
    public List<Product> getListOfProducts(){
        return productsRepo.findAll();
    }
}
