package com.example.effectivemobileentertask.Controller;

import com.example.effectivemobileentertask.Entity.*;
import com.example.effectivemobileentertask.Repository.OrganizationRepository;
import com.example.effectivemobileentertask.Repository.ProductsRepo;
import com.example.effectivemobileentertask.Repository.SalesInfoRepository;
import com.example.effectivemobileentertask.Repository.UserService;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class ProductController {
    @Autowired
    private ProductsRepo productsRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private SalesInfoRepository salesInfoRepository;


    @GetMapping("/admin/addNewProduct")
    public String product(Model model){
        model.addAttribute("product", new Product());
        return "addNewProduct";
    }


    @PostMapping("/admin/addNewProduct")
    public String makeProduct(@ModelAttribute("product") Product product){
        product.setOrganization(organizationRepository.findById(102L).get());
        product.setActive(true);
        productsRepo.save(product);
        return "redirect:/products";
    }

    public User getCurrentUser(Organization organization){
        for (User user: userService.getAllUsers()) {
            if (user.getOrganizations().contains(organization)){
                return user;
            }
        }
        return null;
    }

    //TODO
    @GetMapping("/addNewProductByOrganization/{id}")
    public String productByOrg(@PathVariable Long id, Model model){
        //System.out.println(organizationRepository.findById(id).get().getProducts());
        model.addAttribute("organization", organizationRepository.findById(id));
        model.addAttribute("product", new Product());
        return "addNewProductByOrganization";
    }

    //TODO
    @PostMapping("/addNewProductByOrganization/{id}")
    public String makeProductByOrg(@PathVariable Long id,
                              @ModelAttribute("product") Product product){
        Organization organization = organizationRepository.findById(id).get();

        product.setId(null);
        product.setActive(false);
        organization.getProducts().add(product);
        product.setOrganization(organization);

        productsRepo.save(product);
        organizationRepository.save(organization);

        return "redirect:/products";
    }

    public static void checkCurrentSales(SalesInfoRepository salesInfoRepository,
                                         ProductsRepo productsRepo){
        Date currentDate = new Date();
        for (SalesInfo sale: salesInfoRepository.findAll()) {
            for (Product product: sale.getProducts()) {
                if (currentDate.getTime() - sale.getStartOfTheSale().getTime() > sale.getDurationInDays()* 86400000L){
                    product.setPrice(product.getInfoAboutSales());
                    product.setInfoAboutSales(0.0);
                    salesInfoRepository.delete(sale);
                    productsRepo.save(product);
                }
            }
        }
    }

    @GetMapping("/products")
    public String getListOfProducts(Model model,
                                    @ModelAttribute("lowBalance") String lowBalance){
        checkCurrentSales(salesInfoRepository,productsRepo);
        model.addAttribute("products",
                productsRepo.findAll().stream().filter(Product::isActive).collect(Collectors.toSet()));
        return "products";
    }

    public User whoIsOwner(Product product){
        for (User user: userService.getAllUsers()) {
            Set<Organization> organizationSet = user.getOrganizations();
            for (Organization organization: organizationSet) {
                if (organization.getProducts().contains(product)){
                    return user;
                }
            }
        }
        return null;
    }

    @GetMapping("/buyProduct/{id}")
    public String buyProduct(@PathVariable Long id,
                             Principal principal,
                             Model model){
        User user = (User) userService.getUserByUsername(principal.getName());
        Product product = productsRepo.findById(id).get();

        for (Organization org: user.getOrganizations()) {
            if (org.getProducts().contains(product)){
                return "redirect:/products";
            }
        }

        User owner = whoIsOwner(product);

        if (user.getBalance() < product.getPrice()){
            model.addAttribute("lowBalance", "You don`t have enough money :(");
            return "redirect:/products";
        }else {
            user.setBalance(user.getBalance() - product.getPrice());
            product.setDateAfterBuying(new Date());
            if (owner != null){
                owner.setBalance(owner.getBalance() + product.getPrice());
            }
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

    @GetMapping("/leaveFeedback/{id}")
    public String feedback(@PathVariable Long id, Model model){
        //model.addAttribute("product", productsRepo.findById(id));
        model.addAttribute("review", "");
        model.addAttribute("grade", 0.0);
        model.addAttribute("product", productsRepo.findById(id));
        return "createReview";
    }

    @PostMapping("/createReview/{id}")
    public String review(@PathVariable Long id,
                         @ModelAttribute("review") String str,
                         @ModelAttribute("grade") Double grade){
        Product product = productsRepo.findById(id).get();
        if (product.getReviews() == null){
            product.setReviews(Collections.singleton(str));
        }else {
            product.getReviews().add(str);
        }

        if (product.getGrades() == null){
            product.setGrades(Collections.singleton(grade));
        }else {
            product.getGrades().add(grade);
        }

        productsRepo.save(product);
        return "redirect:/myPurchases";
    }

    @GetMapping("/reviews/{id}")
    public String reviews(@PathVariable Long id, Model model){
        model.addAttribute("reviews", productsRepo.findById(id).get().getReviews());
        model.addAttribute("grades", productsRepo.findById(id).get().getGrades());
        return "reviews";
    }

    @GetMapping("/makeReturn/{id}")
    public String makeReturn(@PathVariable Long id, Principal principal){
        Product product = productsRepo.findById(id).get();
        User user = (User) userService.getUserByUsername(principal.getName());

        Date currentDate = new Date();

        if (currentDate.getTime() - product.getDateAfterBuying().getTime() > 60000){
            return "redirect:/myPurchases";
        }

        product.setNumOnTheStock(product.getNumOnTheStock() + 1);
        user.setBalance(user.getBalance() + product.getPrice());
        user.getProducts().remove(product);

        productsRepo.save(product);
        userService.saveUser(user);

        return "redirect:/myPurchases";
    }
}
