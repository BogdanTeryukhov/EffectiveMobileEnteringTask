package com.example.effectivemobileentertask.Controller;

import com.example.effectivemobileentertask.Entity.Product;
import com.example.effectivemobileentertask.Entity.User;
import com.example.effectivemobileentertask.Repository.ProductsRepo;
import com.example.effectivemobileentertask.Repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private UserService userService;

    @GetMapping("/admin/addNewProduct")
    public String product(Model model){
        model.addAttribute("product", new Product());
        return "addNewProduct";
    }

    @PostMapping("/admin/addNewProduct")
    public String makeProduct(@ModelAttribute("product") Product product){
        productsRepo.save(product);
        return "redirect:/home";
    }

    @GetMapping("/products")
    public String getListOfProducts(Model model,Principal principal,
                                    @ModelAttribute("lowBalance") String lowBalance){
        model.addAttribute("products",productsRepo.findAll());
        User user = (User) userService.getUserByUsername(principal.getName());
        System.out.println(user.toString());
        return "products";
    }

    @GetMapping("/buyProduct/{id}")
    public String buyProduct(@PathVariable Long id,
                             Principal principal,
                             Model model){
        User user = (User) userService.getUserByUsername(principal.getName());
        Product product = productsRepo.findById(id).get();

        if (user.getBalance() < product.getPrice()){
            model.addAttribute("lowBalance", "You don`t have enough money :(");
            return "redirect:/products";
        }else {
            user.setBalance(user.getBalance() - product.getPrice());
            product.setNumOnTheStock(product.getNumOnTheStock() - 1);
            if (product.getNumOnTheStock() == 0){
                productsRepo.delete(product);
                return "redirect:/products";
            }
            user.getProducts().add(product);

            userService.saveUser(user);
            productsRepo.save(product);
        }

        return "redirect:/products";
    }

    @GetMapping("/myPurchases")
    public String myPurchases(Model model,
                              Principal principal){
        User user = (User)userService.getUserByUsername(principal.getName());
        model.addAttribute("purchases", user.getProducts());
        return "myPurchases";
    }
}
