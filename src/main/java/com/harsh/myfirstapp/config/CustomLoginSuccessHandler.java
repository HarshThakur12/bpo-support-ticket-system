package com.harsh.myfirstapp.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // Logged-in user ke roles (authorities) uthao
        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

        // Role check karke sahi page par redirect karo
        if (roles.contains("ROLE_ADMIN")) {
            response.sendRedirect("/index.html"); // Admin ko full dashboard dikhao
        } else if (roles.contains("ROLE_USER")) {
            response.sendRedirect("/create-ticket.html"); // User ko sirf ticket banane wala page dikhao
        } else {
            response.sendRedirect("/login.html?error");
        }
    }
}