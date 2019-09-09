package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserListNew {

    @Autowired
    UserService userService;

    @GetMapping("/userList")
    public String getUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userListNew";
    }

}
