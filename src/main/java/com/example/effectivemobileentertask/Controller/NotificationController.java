package com.example.effectivemobileentertask.Controller;


import com.example.effectivemobileentertask.Entity.Notification;
import com.example.effectivemobileentertask.Entity.User;
import com.example.effectivemobileentertask.Repository.NotificationsRepository;
import com.example.effectivemobileentertask.Repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.Date;


@Controller
public class NotificationController {

    @Autowired
    private UserService userService;

    @Autowired
    private NotificationsRepository notificationsRepository;

    @GetMapping("/myNotifications")
    public String myNotifications(Principal principal, Model model){
        User user = (User)userService.getUserByUsername(principal.getName());
        model.addAttribute("notifications", user.getNotifications());
        return "myNotifications";
    }

    @GetMapping("/admin/users/addNotification/{id}")
    public String createNotification(Model model,
                                     @PathVariable Long id){
        model.addAttribute("user_id", id);
        model.addAttribute("notification", new Notification());
        //System.out.println(new Notification());
        return "addNotification";
    }

    @PostMapping("/admin/users/addNote/{id}")
    public String addNotification(@ModelAttribute("notification") Notification notification,
                                  @PathVariable Long id,
                                  Principal principal){
        notification.setDate(new Date());
        notification.setId(null);
        notificationsRepository.save(notification);

        System.out.println(notification);
        User user = userService.getUserById(id);
        user.getNotifications().add(notification);
        userService.saveUser(user);


        return "redirect:/admin/users";
    }

}
