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
                .csrf(csrf -> csrf.disable()) // Testing ke liye disable
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/index.html", "/css/**", "/js/**").permitAll() // Public files[cite: 1]
                        .anyRequest().authenticated() // Protected[cite: 1]
                )
                .formLogin(form -> form
                        .loginPage("/login.html") // Tumhara custom page
                        .loginProcessingUrl("/login") // Ye default hi rehta hai
                        .defaultSuccessUrl("/index.html", true)
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    // Yahan BCryptPasswordEncoder ka bean add kar dete hain taaki future mein kaam aaye
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Abhi ke liye plain text password[cite: 1]
    }
}