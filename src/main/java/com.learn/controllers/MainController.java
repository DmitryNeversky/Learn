package com.learn.controllers;

import com.learn.entities.Lobby;
import com.learn.entities.Room;
import com.learn.entities.Message;
import com.learn.entities.User;
import com.learn.repositories.RoomRepository;
import com.learn.repositories.LobbyRepository;
import com.learn.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class MainController {

    @Value("${upload.path}")
    private String upPath;

    private final UserRepository userRepository;
    private final LobbyRepository lobbyRepository;      // 2<
    private final RoomRepository roomRepository;        // 1x1

    public MainController(UserRepository userRepository, LobbyRepository lobbyRepository, RoomRepository roomRepository) {
        this.userRepository = userRepository;
        this.lobbyRepository = lobbyRepository;
        this.roomRepository = roomRepository;
    }

    @MessageMapping("/chat")
    @SendTo("/topic/chat")
    public boolean getMessage(){
        return true;
    }

    @GetMapping("/lobby-{id}")
    public ModelAndView joinLobby(@PathVariable(value = "id") long id, @ModelAttribute Model model) {

        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addAllObjects(model.asMap());

        return modelAndView;
    }

    @GetMapping("/")
    public ModelAndView main(@ModelAttribute Model model) {
        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addAllObjects(model.asMap());

        return modelAndView;
    }

    @GetMapping("/room-{id}")
    public ModelAndView joinRoom(@PathVariable(value = "id") long id, @AuthenticationPrincipal User auth, @ModelAttribute Model model){

        ModelAndView modelAndView = new ModelAndView("chat");
        modelAndView.addAllObjects(model.asMap());

        User user1 = userRepository.findById(id);
        User user2 = userRepository.findById((long) auth.getId());

        Room room;

        if(roomRepository.existsByUser1AndUser2(user1, user2) || roomRepository.existsByUser1AndUser2(user2, user1)){
            System.out.println("Существует");
            room = roomRepository.findByUser1AndUser2(user1, user2);
        } else {
            System.out.println("Не существует");
            room = new Room(user1, user2, new ArrayList<>());
            roomRepository.save(room);
        }

        // Сообщения

        Iterable<Message> messages = room.getMessageList();
        model.addAttribute("messages", messages);

        return modelAndView;
    }

    @ResponseBody
    @PostMapping("/room-{room_id}")
    public void add(@PathVariable(value = "room_id") long room_id, @RequestParam String letter,  @RequestParam(required = false) List<MultipartFile> multipartImages, @RequestParam(required = false) List<MultipartFile> multipartFiles, @AuthenticationPrincipal User author) {

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

        Room room = roomRepository.findById(room_id);

        room.getMessageList().add(message);

        System.out.println(room.getMessageList());

        roomRepository.save(room);

        roomRepository.flush();
    }

//    private Message task(MultipartFile file){
//
//
//        return message;
//    }

    @ModelAttribute
    public Model getModel(Model model){

        // Сообщения
//        Iterable<Message> messages = messageRepository.findAll();
//        model.addAttribute("messages", messages);

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