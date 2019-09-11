package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.config.EncriptionConfig;
import com.instahipsta.webappTest.domain.Role;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.repos.UserRepo;
import com.instahipsta.webappTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.spec.EncodedKeySpec;
import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/userList")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for (String key : form.keySet()) {
            if (roles.contains(key)) {
                user.getRoles().add(Role.valueOf(key));
            }
        }

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

        return "redirect:/userList";
    }

    @PostMapping("/confirmPassword")
    public String deleteUser(@AuthenticationPrincipal User currentUser,
                             Model model,
                             @RequestParam String password,
                             @RequestParam("userId") User user) {
        boolean isPasswordConfirm = passwordEncoder.matches(password, currentUser.getPassword());
        boolean isCurrentAccount = user.getId() == currentUser.getId();

        model.addAttribute("user", user);

        if (!isPasswordConfirm || password.isEmpty() || isCurrentAccount) {
            model.addAttribute("passwordError", "Bad credentials");
            return  "userEdit";
        }
        else {
            userRepo.deleteById(user.getId());
            return "redirect:/userList";
        }


    }
}
