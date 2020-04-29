package com.learn.entities;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "usr")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String avatarPath;
    private boolean status;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    // ..?
    @ManyToMany(mappedBy="users")
    private Set<Lobby> lobbies = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    // Указывает, истек ли срок действия учетной записи пользователя
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // Указывает, заблокирован ли пользователь или разблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // Указывает, истек ли срок действия учетных данных пользователя (пароля)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // Online/Offline
    @Override
    public boolean isEnabled() {
        return status;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public String getStatus() {
        if(status)
            return "Online";
        else
            return "Offline";
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Set<Lobby> getLobbies() {
        return lobbies;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", avatarPath='" + avatarPath + '\'' +
                ", status=" + status +
                ", roles=" + roles +
                ", lobbies=" + lobbies +
                '}';
    }
}
