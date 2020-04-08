package com.learn.controllers;

import com.learn.entities.Message;
import com.learn.entities.User;
import com.learn.repositories.MessageRepository;
import com.learn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.*;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String upPath;

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
    public void add(@RequestParam String letter,  @RequestParam(required = false) List<MultipartFile> multipartImages, @RequestParam(required = false) List<MultipartFile> multipartFiles, @AuthenticationPrincipal User author, @ModelAttribute Model model) {

        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm");

        Message message = new Message(letter, formatForDateNow.format(date), author);

        List<String> listImages = new ArrayList<>();
        List<String> listFiles = new ArrayList<>();

        for(MultipartFile pair : multipartImages) {
            try {
                String fileName = pair.getOriginalFilename();

                pair.transferTo(new File(upPath + "/images/" + fileName));

                listImages.add(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        for(MultipartFile pair : multipartFiles) {
            try {
                String fileName = pair.getOriginalFilename();

                pair.transferTo(new File(upPath + "/files/" + fileName));

                listFiles.add(fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        message.setImageNames(listImages);
        message.setFileNames(listFiles);

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

        return model;
    }
}