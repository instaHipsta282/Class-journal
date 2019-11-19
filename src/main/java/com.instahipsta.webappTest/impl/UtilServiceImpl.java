package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UtilServiceImpl implements UtilService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    //testing
    @Override
    public LocalDate stringToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        return LocalDate.parse(date, formatter);
    }

    @Override
    public Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    //testing
    @Override
    public boolean checkPassword(String password, User user, Model model) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("oldPasswordError", "The password is failed");
            model.addAttribute("passwordError", "The password is failed");
            return false;
        }
        else return true;
    }

    //testing
    @Override
    public boolean checkNewPassword(String newPassword, String newPasswordRe, String password, Model model) {
            if (!newPassword.equals(newPasswordRe)) {
                model.addAttribute("newPasswordError",
                        "The new password and new repeated password fields are different");
            }
            else if (newPassword.equals(password)) {
                model.addAttribute("newPasswordError", "Your new password cannot be equals with old password");
            }
            else return true;

            return false;
    }

    //testing
    @Override
    public boolean checkNewEmail(User user, String newEmail, Model model) {
        if (user.getEmail().equals(newEmail)) {
            model.addAttribute("newEmailError", "You are already using this email");
            return false;
        }
        return true;
    }
    //testing
    @Override
    public boolean checkNewPhone(User user, String newPhone, Model model) {
        if (newPhone.equals(user.getPhone())) {
            model.addAttribute("oldPhoneError", "The phone numbers cannot be equals");
            return false;
        }
        return true;
    }
}
