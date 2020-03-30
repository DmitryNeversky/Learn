package com.learn.controllers;

import com.learn.entities.Message;
import com.learn.entities.User;
import com.learn.repositories.MessageRepository;
import com.learn.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Controller
public class MainController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MainController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/main")
    public ModelAndView main(@ModelAttribute Model model) {

        ModelAndView modelAndView = new ModelAndView("main");
        modelAndView.addAllObjects(model.asMap());

        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/main")
    public void add(@RequestParam String letter,  @RequestParam MultipartFile multipartFile, @AuthenticationPrincipal User author, @ModelAttribute Model model) {

        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm");

        Message message = new Message(letter, formatForDateNow.format(date), author);

        if(multipartFile != null) {
            try {
                String uID = UUID.randomUUID().toString();
                multipartFile.transferTo(new File("C://" + uID));
                System.out.println("загружено");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("null");
        }

        messageRepository.save(message);
    }

    @GetMapping("/clear")
    public String clear(){
        messageRepository.deleteAll();
        return "redirect:/main";
    }

    @ModelAttribute
    public Model getModel(Model model){

        // Сообщения
        Iterable<Message> messages = messageRepository.findAll();
        model.addAttribute("messages", messages);

        // Пользователи

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        // Кол-во пользователей

        String ucount = String.valueOf(userRepository.count());
        model.addAttribute("ucount", ucount + " участников");

        // Отдельное имя участника

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uname = userRepository.findByUsername(authentication.getName()).getUsername();
        model.addAttribute("uname", uname);

        // Файлы

//        model.addAttribute("file", storageService.load(""));

        return model;
    }
}