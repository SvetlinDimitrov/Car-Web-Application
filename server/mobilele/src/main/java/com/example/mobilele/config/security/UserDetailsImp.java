package com.example.mobilele.config.security;

import com.example.mobilele.services.UserServiceImp;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;



@Component
@AllArgsConstructor
public class UserDetailsImp implements UserDetailsService {

    private final UserServiceImp userService;

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserPrincipal(userService.getByUsername(username));
    }
}
