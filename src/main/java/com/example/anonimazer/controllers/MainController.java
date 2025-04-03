package com.example.anonimazer.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/info")
    public String help(){
        return "info";
    }

    @GetMapping("/")
    public String startPage(){
        return "startPage";
    }
}
