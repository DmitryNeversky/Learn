package com.learn.controllers;

import com.learn.entities.Role;
import com.learn.entities.User;
import com.learn.repositories.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;

@Controller
public class RegController {

    private final UserRepository userRepository;

    public RegController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/reg")
    public String getRegistration() {
        return "reg";
    }

    @PostMapping("/reg")
    public String registration(User user, Model model){

        User userFromData = userRepository.findByUsername(user.getUsername());

        if(userFromData != null){
            model.addAttribute("error", "Аккаунт уже создан");
            return "reg";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepository.save(user);

        return "redirect:/main";
    }
}