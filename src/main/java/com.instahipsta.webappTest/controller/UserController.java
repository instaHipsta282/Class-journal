package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Role;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.repos.UserRepo;
import com.instahipsta.webappTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}")
    public String userEditForm(@PathVariable User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "userEdit";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping
    public String userSave(
            @RequestParam Map<String, String> form,
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam String lastName,
            @RequestParam String firstName,
            @RequestParam String secondName,
            @RequestParam("userId") User user) {

        if (!username.isEmpty() && !username.equals(user.getUsername())) {
            user.setUsername(username);
        }

        if (!email.isEmpty() && !email.equals(user.getEmail())) {
            user.setEmail(email);
        }

        if (!phone.isEmpty() && !phone.equals(user.getPhone())) {
            user.setPhone(phone);
        }

        if (!lastName.isEmpty() && !lastName.equals(user.getLastName())) {
            user.setLastName(lastName);
        }

        if (!firstName.isEmpty() && !firstName.equals(user.getFirstName())) {
            user.setFirstName(firstName);
        }

        if (!secondName.isEmpty() && !secondName.equals(user.getSecondName())) {
            user.setSecondName(secondName);
        }

        userRepo.save(user);

        return "redirect:/user";
    }
}
