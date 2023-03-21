package com.example.effectivemobileentertask.Service;

import com.example.effectivemobileentertask.Entity.User;
import com.example.effectivemobileentertask.Repository.UserRepo;
import com.example.effectivemobileentertask.Repository.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo userRepo;

    public UserServiceImpl(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepo.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepo.findById(id).get();
    }

    @Override
    public void updateUser(User user) {
        userRepo.save(user);
    }

    @Override
    public void deleteUserById(Long Id) {
        userRepo.deleteById(Id);
    }

    @Override
    public User getByEmail(String email) {
        return userRepo.findByEmail(email).get();
    }

    @Override
    public boolean isExists(String email) {
        return userRepo.findByEmail(email).isPresent();
    }
}
