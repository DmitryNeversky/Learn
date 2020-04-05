package com.learn.controllers;

import com.learn.entities.Role;
import com.learn.entities.User;
import com.learn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

@Controller
public class RegController {

    @Value("${upload.path}")
    private String upPath;

    private final UserRepository userRepository;

    public RegController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/reg")
    public String getRegistration() {
        return "reg";
    }

    @PostMapping("/reg")
    public String registration(@RequestParam(required = false) MultipartFile multipleAvatar, User user, Model model){

        User userFromData = userRepository.findByUsername(user.getUsername());

        if(userFromData != null){
            model.addAttribute("error", "Аккаунт уже создан");
            return "reg";
        }

        if(multipleAvatar != null) {

            try {
                String fileName = UUID.randomUUID() + "." + multipleAvatar.getOriginalFilename();

                multipleAvatar.transferTo(new File(upPath + "/" + fileName));

                user.setAvatarPath(fileName);
            } catch (IOException e) {
                model.addAttribute("error", e.getCause());
            }

        } else {
            user.setAvatarPath("default.png");
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/main";
    }
}