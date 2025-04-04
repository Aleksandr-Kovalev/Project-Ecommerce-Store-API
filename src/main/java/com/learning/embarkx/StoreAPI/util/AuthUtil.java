package com.learning.embarkx.StoreAPI.util;

import com.learning.embarkx.StoreAPI.model.User;
import com.learning.embarkx.StoreAPI.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthUtil {

    private final UserRepository userRepository;

    public String loggedInEmail(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + auth.getName()));
        return user.getEmail();
    }

    public Long loggedInUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + auth.getName()));
        return user.getUserId();
    }

    public User loggedInUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByUserName(auth.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + auth.getName()));
        return user;
    }

}
