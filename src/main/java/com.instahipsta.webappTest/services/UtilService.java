package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.User;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.time.LocalDate;
import java.util.Map;

public interface UtilService {
    LocalDate stringToLocalDate(String date);

    Map<String, String> getErrors(BindingResult bindingResult);

    boolean checkPassword(String password, User user, Model model);

    boolean checkNewPassword(String newPassword, String newPasswordRe, String password, Model model);

    boolean checkNewEmail(User user, String email, Model model);

    //testing
    boolean checkNewPhone(User user, String newPhone, Model model);
}
