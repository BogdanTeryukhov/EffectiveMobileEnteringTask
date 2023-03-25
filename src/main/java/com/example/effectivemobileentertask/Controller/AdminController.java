package com.example.effectivemobileentertask.Controller;

import com.example.effectivemobileentertask.Entity.Notification;
import com.example.effectivemobileentertask.Entity.Organization;
import com.example.effectivemobileentertask.Entity.Product;
import com.example.effectivemobileentertask.Entity.User;
import com.example.effectivemobileentertask.Repository.NotificationsRepository;
import com.example.effectivemobileentertask.Repository.OrganizationRepository;
import com.example.effectivemobileentertask.Repository.ProductsRepo;
import com.example.effectivemobileentertask.Repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/users")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private ProductsRepo productsRepo;


    public void findAndSaveUserOfTheOrg(Organization organization,
                                        Notification notification){
        for (User user: userService.getAllUsers()) {
            if (user.getOrganizations().contains(organization)){
                user.getNotifications().add(notification);
                userService.saveUser(user);
            }
        }
    }

    @GetMapping("/getAllOrgs")
    public String getAllOrgs(Model model){
        model.addAttribute("allOrgs",
                organizationRepository.findAll().stream().filter(Organization::isActive).collect(Collectors.toSet()));
        return "getAllOrgs";
    }

    @GetMapping("/deleteOrganization/{id}")
    public String deleteOrganization(@PathVariable Long id) {
        Organization organization = organizationRepository.findById(id).get();

        Notification notification =
                new Notification("Info about your organization appliance",
                        "The " + organization.getName() + " organization has been banned:(");
        notification.setDate(new Date());
        notificationsRepository.save(notification);

        findAndSaveUserOfTheOrg(organization, notification);

        organizationRepository.delete(organization);

        return "redirect:/admin/users";
    }

    @GetMapping("/freezeOrganization/{id}")
    public String freezeOrganization(@PathVariable Long id) {
        Organization organization = organizationRepository.findById(id).get();
        organization.setActive(false);

        organization.getProducts().forEach(product -> product.setActive(false));
        organizationRepository.save(organization);

        Notification notification =
                new Notification("Info about your organization appliance",
                        "The " + organization.getName() + " organization has been banned:(");
        notification.setDate(new Date());
        notificationsRepository.save(notification);

        findAndSaveUserOfTheOrg(organization, notification);

        return "redirect:/admin/users";
    }


    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "editUser";
    }

    @GetMapping("/freeze/{id}")
    public String freeze(@PathVariable Long id) {
        userService.getUserById(id).setActive(false);
        userService.updateUser(userService.getUserById(id));
        return "redirect:/admin/users";
    }

    @GetMapping
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/users";
    }

    @PostMapping("/{id}")
    public String updateUser(@PathVariable Long id,
                             @ModelAttribute("user") User user) {
        User existingUser = userService.getUserById(id);

        existingUser.setId(user.getId());
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());
        existingUser.setBalance(user.getBalance());

        userService.updateUser(existingUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/usersPurchases/{id}")
    public String userPurchases(@PathVariable Long id,
                                Model model) {
        model.addAttribute("purchases", userService.getUserById(id).getProducts());
        return "myPurchases";
    }

    @GetMapping("/getListOfAppliances")
    public String getListOfAppliances(Model model){
        Set<Organization> nonActivateOrganizations = organizationRepository.findAll().stream().filter(organization -> !organization.isActive()).collect(Collectors.toSet());
        model.addAttribute("organizations", nonActivateOrganizations);
        return "getListOfAppliances";
    }

    @GetMapping("/activateTheOrg/{id}")
    public String activateTheOrganization(@PathVariable Long id){
        Organization organization = organizationRepository.findById(id).get();
        organization.setActive(true);
        organization.getProducts().forEach(product -> product.setActive(true));
        organizationRepository.save(organization);

        Notification notification =
                new Notification("Info about your organization appliance",
                        "The " + organization.getName() + " organization has been activated");
        notification.setDate(new Date());
        notificationsRepository.save(notification);

        findAndSaveUserOfTheOrg(organization, notification);
        return "redirect:/admin/users";
    }

    @GetMapping("/productsAppliances")
    public String productsAppliances(Model model){
        Set<Product> nonActivateProducts = productsRepo.findAll().stream().filter(product -> !product.isActive()).collect(Collectors.toSet());
        model.addAttribute("products", nonActivateProducts);
        return "productsAppliances";
    }

    @GetMapping("/activateTheProduct/{id}")
    public String activateTheProduct(@PathVariable Long id){
        Product product = productsRepo.findById(id).get();
        product.setActive(true);
        productsRepo.save(product);

        Notification notification =
                new Notification("Info about your product appliance",
                        "The " + product.getName() + " product has been activated");
        notification.setDate(new Date());
        notificationsRepository.save(notification);

        Organization organization = product.getOrganization();


        for (User user: userService.getAllUsers()) {
            if (user.getOrganizations().contains(organization)){
                user.getNotifications().add(notification);
                userService.saveUser(user);
            }
        }
        return "redirect:/admin/users";
    }
}
