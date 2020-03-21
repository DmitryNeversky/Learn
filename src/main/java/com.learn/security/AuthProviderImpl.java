package com.learn.security;

import com.learn.entities.Role;
import com.learn.entities.User;
import com.learn.repositories.UserRepository;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthProviderImpl implements AuthenticationProvider {

    private final UserRepository userRepository;

    public AuthProviderImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userRepository.findByUsername(authentication.getName());
        if(user == null)
            throw new UsernameNotFoundException("Пользователь не найден");
        if(!user.getPassword().equals(authentication.getCredentials().toString()))
            throw new BadCredentialsException("Пароли не совпадают");

        user.setActive(true);
        userRepository.save(user);

        List<GrantedAuthority> authorities = Arrays.asList(Role.values());
        return new UsernamePasswordAuthenticationToken(user, null, authorities);
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
