package com.learn.controllers;

import com.learn.entities.User;
import com.learn.repositories.UserRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @MessageMapping("/status")
    @SendTo("/topic/status")
    public boolean setStatus(String status, Principal principal){
        User user = userRepository.findByUsername(principal.getName());

        if (status.equals("true")) user.setStatus(true);
        else user.setStatus(false);

        userRepository.save(user);

        System.out.println("СТАТУС");

        return true;
    }
}
