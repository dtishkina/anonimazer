package com.example.anonimazer.rest.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anonimazer")
public class PhotoRestController {

    @GetMapping("/upload")
    public void upload(){

    }
}
