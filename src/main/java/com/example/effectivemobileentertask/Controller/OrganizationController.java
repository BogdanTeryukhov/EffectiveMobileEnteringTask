package com.example.effectivemobileentertask.Controller;

import com.example.effectivemobileentertask.Entity.Organization;
import com.example.effectivemobileentertask.Entity.User;
import com.example.effectivemobileentertask.Repository.OrganizationRepository;
import com.example.effectivemobileentertask.Repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.stream.Collectors;

@Controller
public class OrganizationController {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/myOrganizations")
    public String myOrganizations(Principal principal, Model model){
        User user = (User) userService.getUserByUsername(principal.getName());
        model.addAttribute("myOrganizations",
                user.getOrganizations().stream().filter(Organization::isActive).collect(Collectors.toSet()));
        return "myOrganizations";
    }

    @GetMapping("/createOrganization")
    public String applyForOrganization(Model model){
        model.addAttribute("organization", new Organization());
        return "createOrganization";
    }

    @PostMapping("/createOrganization")
    public String applyForOrg(@ModelAttribute("organization") Organization organization,
                            Principal principal){
        organization.setActive(false);
        organizationRepository.save(organization);

        User user = (User) userService.getUserByUsername(principal.getName());
        user.getOrganizations().add(organization);
        userService.saveUser(user);
        return "redirect:/products";
    }
}
