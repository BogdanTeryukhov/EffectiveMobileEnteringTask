package com.example.effectivemobileentertask.Repository;

import com.example.effectivemobileentertask.Entity.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface UserService {
    List<User> getAllUsers();
    void saveUser(User user);
    User getUserById(Long id);
    void updateUser(User user);
    void deleteUserById(Long Id);
    User getByEmail(String email);
    boolean isExists(String email);

}
