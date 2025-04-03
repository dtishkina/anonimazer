package com.example.anonimazer.controllers;

import com.example.anonimazer.models.Photo;
import com.example.anonimazer.models.User;
import com.example.anonimazer.repo.PhotoRepository;
import com.example.anonimazer.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;

@Controller
@RequestMapping("/anonimazer/photo")
public class PhotoController {

    private static final String UPLOAD_DIR = "uploads";

    @Autowired
    private UserService userService;

    @Autowired
    private PhotoRepository photoRepository;

    @GetMapping
    public String uploadPhoto(){
        return "uploadPhoto";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) throws IOException {

        if (file.isEmpty()) {
            return "redirect:/upload.html";
        }

        // Создаём папку uploads, если нет
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // Сохраняем файл
        String filename = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path destination = uploadPath.resolve(filename);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        User user = userService.findByLogin(username).orElseThrow(); // получаем текущего пользователя

        Photo photo = new Photo();
        photo.setFilename(filename);
        photo.setOwner(user);
        photoRepository.save(photo);

        // Перенаправляем на страницу результата
        redirectAttributes.addAttribute("filename", filename);
        return "redirect:/anonimazer/photo/result";
    }

    @GetMapping("/result")
    public String showResult(@RequestParam("filename") String filename, Model model, Principal principal) {
        Photo photo = photoRepository.findByFilename(filename)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        // проверка, текущий ли пользователь — владелец
        if (!photo.getOwner().getLogin().equals(principal.getName())) {
            System.out.println("redirect");
            return "forbidden";
        }

        model.addAttribute("filename", filename);
        return "result";
    }
}
