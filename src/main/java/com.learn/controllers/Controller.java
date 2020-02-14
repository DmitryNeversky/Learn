package com.learn.controllers;

import com.learn.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.learn.repositories.UserRepository;

@org.springframework.stereotype.Controller
public class Controller {
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String main(Model model){
        Iterable<User> pair = userRepository.findAll();
        model.addAttribute("userList", pair);

        return "index";
    }

    @PostMapping
    public String post(@RequestParam String email, @RequestParam String password){
        User user = new User(email, password);
        userRepository.save(user);

        return "index";
    }
}
