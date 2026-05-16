package com.harsh.myfirstapp.config;

import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private CustomLoginSuccessHandler successHandler; // Pehle upar autowired kar lo

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Testing ke liye disable
                .authorizeHttpRequests(auth -> auth
                        // 1. Login page, CSS, JS aur ticket CREATE karne wali API sabke liye OPEN hai
                        .requestMatchers("/login.html", "/css/**", "/js/**", "/status", "/create-ticket", "/create-ticket.html").permitAll()

                        // 2. Dashboard dekhna, Delete karna, Update karna sirf ADMIN ka kaam hai
                        .requestMatchers("/index.html", "/all-tickets", "/delete-ticket/**", "/update-ticket/**", "/tickets/**").hasRole("ADMIN")

                        // 3. Baaki sabke liye login zaroori hai
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login.html")
                        .loginProcessingUrl("/login")
//                        .defaultSuccessUrl("/index.html", true)
                        .successHandler(successHandler) // defaultSuccessUrl ki jagah ye aayega
                        .permitAll()
                )
                .logout(logout -> logout.permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}