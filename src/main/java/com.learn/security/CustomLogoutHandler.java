package com.learn.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.learn.entities.User;
import com.learn.repositories.UserRepository;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutHandler implements LogoutHandler {

    private final UserRepository userRepository;

    public CustomLogoutHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {
        String name = authentication.getName();
        User user = userRepository.findByUsername(name);
        if(user == null)
            return;

//        user.setActive(false);
        userRepository.save(user);
    }
}