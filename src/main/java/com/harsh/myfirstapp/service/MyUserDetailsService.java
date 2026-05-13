package com.harsh.myfirstapp.service;

import com.harsh.myfirstapp.entity.User;
import com.harsh.myfirstapp.repository.UserRepository;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsPasswordService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

    @Service
    public class MyUserDetailsService implements UserDetailsService, UserDetailsPasswordService {

        @Autowired
        private UserRepository repo; // Detective ke paas Register (Repository) ka access hona chahiye

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            System.out.println("Trying to login with user: " + username); //
            // 1. Database mein user ko dhoondho
            Optional<User> user = repo.findByUsername(username);

            // 2. Agar nahi mila toh error feko
            if (user.isEmpty()) {
                System.out.println("User Not Found in Database!");
                throw new UsernameNotFoundException("User not found");
            }
            System.out.println("User found! Password is: " + user.get().getPassword()); // Ye bhi
            // 3. Agar mil gaya, toh Spring Security ko batao ki ye banda valid hai
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.get().getUsername())
                    .password(user.get().getPassword()) // {noop} matlab abhi password encode nahi hai
                    .roles(user.get().getRole())
                    .build();
        }

        @Override
        public UserDetails updatePassword(UserDetails user, @Nullable String newPassword) {
            return null;
        }
    }
