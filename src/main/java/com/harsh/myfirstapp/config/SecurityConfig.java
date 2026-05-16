package com.harsh.myfirstapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Testing aur Postman ke liye disable
                .authorizeHttpRequests(auth -> auth
                        // 1. `/login.html`, CSS, JS aur ticket CREATE karne wala endpoint PUBLIC rahega (Bina login ke chalega)
                        .requestMatchers("/login.html", "/css/**", "/js/**", "/status", "/create-ticket").permitAll()

                        // 2. Iske alawa baki saare endpoints (jaise /all-tickets, /update-ticket, /index.html) ke liye LOGIN zaroori hai
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html") // Tumhara custom login page
                        .loginProcessingUrl("/login") // Spring Security ka internal processing URL
                        .defaultSuccessUrl("/index.html", true) // Login hote hi seedha dashboard (index.html) par bhejo
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Plain text passwords ke liye
    }
}