package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.repos.UserRepo;
import com.instahipsta.webappTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class NewUserController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/profileNew")
    public String getProfileForm(Model model) {
        return "profileNew";
    }


    @PostMapping("/changePassword")
    public String changePassword(
                               @AuthenticationPrincipal User user,
                               Model model,
                               @RequestParam String oldPassword,
                               @RequestParam String newPassword,
                               @RequestParam String newPasswordRe
    ) {

        System.out.println(passwordEncoder.matches(oldPassword, user.getPassword()));
        boolean isOldPasswordTrue = passwordEncoder.matches(oldPassword, user.getPassword());
        boolean isNewPasswordConfirm = newPassword.equals(newPasswordRe);

        //If user have some error in password edit form, form don`t collapse
        model.addAttribute("somePasswordError", "You have some password error");

        if (oldPassword.isEmpty()) {
            model.addAttribute("oldPasswordError", "The old password field cannot be empty");

        }
        if (!oldPassword.isEmpty() && !isOldPasswordTrue) {
            model.addAttribute("oldPasswordError", "The password is failed");
        }
        if (newPassword.isEmpty()) {
            model.addAttribute("newPasswordError", "The new password field cannot be empty");
        }
        if (newPasswordRe.isEmpty()) {
            model.addAttribute("newPasswordReError", "The repeated new password field cannot be empty");
        }
        if (!newPasswordRe.isEmpty() && !newPassword.isEmpty() && !isNewPasswordConfirm) {
            model.addAttribute("newPasswordError", "The new password and new repeated password fields are different");
        }
        if (!newPassword.isEmpty() && !newPasswordRe.isEmpty()
                && !oldPassword.isEmpty() && isNewPasswordConfirm && isOldPasswordTrue) {
            model.addAttribute("somePasswordError", null);
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepo.save(user);
        }

        return "profileNew";
    }

}



