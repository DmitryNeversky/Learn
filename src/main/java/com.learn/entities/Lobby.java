package com.learn.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Lobby {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<User> users = new ArrayList<>();

    public List<User> getUsers() {
        return users;
    }

    @ElementCollection(fetch = FetchType.LAZY)
    @OneToMany(cascade = CascadeType.ALL)
    private List<Message> messageList = new ArrayList<>();

    public Lobby(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Message> getMessageList() {
        return messageList;
    }
}
