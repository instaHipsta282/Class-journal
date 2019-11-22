package com.instahipsta.webappTest.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String loginPage(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (!auth.getAuthorities().toString().contains("ROLE_ANONYMOUS")) return "redirect:/";

        String referrer = request.getHeader("Referer");
        request.getSession().setAttribute("url_prior_login", referrer);
        return "login";
    }
}
