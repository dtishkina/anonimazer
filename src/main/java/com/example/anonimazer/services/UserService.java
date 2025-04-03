package com.example.anonimazer.services;


import com.example.anonimazer.models.User;
import com.example.anonimazer.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public List<User> findAll() {
        return userRepo.findAll();
    }

    public boolean save(User user) {
        if (userRepo.findFirstByLogin(user.getLogin()).isPresent()){
            return false;
        }
        else {
            userRepo.save(user);
            return true;
        }
    }

    public Optional<User> findByLogin(String login) {
        return userRepo.findFirstByLogin(login);
    }
}
