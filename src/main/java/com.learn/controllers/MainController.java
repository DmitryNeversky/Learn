package com.learn.controllers;

import com.learn.entities.Message;
import com.learn.entities.User;
import com.learn.repositories.MessageRepository;
import com.learn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class MainController {

    private final MessageRepository messageRepository;
    private final UserRepository userRepository;

    public MainController(MessageRepository messageRepository, UserRepository userRepository) {
        this.messageRepository = messageRepository;
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public String home(){
        return "home";
    }

    @GetMapping("/main")
    public String main(Model model) {

        // Сообщения
        Iterable<Message> messages = messageRepository.findAll();

        model.addAttribute("messages", messages);

        // Пользователи

        Iterable<User> users = userRepository.findAll();
        model.addAttribute("users", users);

        // Кол-во пользователей

        String ucount = String.valueOf(userRepository.count());
        model.addAttribute("ucount", ucount + " участников");

        // Статус

        return "main";
    }

    @PostMapping("/main")
    public String add(@RequestParam String text, @AuthenticationPrincipal User author, Model model) {
        Date date = new Date();
        SimpleDateFormat formatForDateNow = new SimpleDateFormat("hh:mm");
        Message message = new Message(text, formatForDateNow.format(date), author);

        messageRepository.save(message);

        Iterable<Message> messages = messageRepository.findAll();

        model.addAttribute("messages", messages);

        return "main";
    }

//    @PostMapping("filter")
//    public String filter(@RequestParam String filter, Map<String, Object> model) {
//        Iterable<Message> messages;
//
//        if (filter != null && !filter.isEmpty()) {
//            messages = messageRepository.findByText(filter);
//        } else {
//            messages = messageRepository.findAll();
//        }
//
//        model.put("messages", messages);
//
//        return "main";
//    }
}