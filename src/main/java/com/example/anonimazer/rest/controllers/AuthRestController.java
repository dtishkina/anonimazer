package com.example.anonimazer.rest.controllers;

import com.example.anonimazer.Role;
import com.example.anonimazer.dto.UserRegistrationDto;
import com.example.anonimazer.models.User;
import com.example.anonimazer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/credential")
public class AuthRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;



    @PostMapping
    public String register(@RequestBody UserRegistrationDto dto) {
        User user = new User();
        user.setLogin(dto.getLogin());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRole(Role.USER.toString());
        System.out.println(user.getLogin());
        System.out.println(userService.save(user));
        return "Пользователь зарегистрирован";
    }
}
