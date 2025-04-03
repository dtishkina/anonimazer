package com.example.anonimazer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/anonimazer/credential")
public class AuthController {

    @GetMapping
    public String signIn() {
        return "signIn";
    }
}
