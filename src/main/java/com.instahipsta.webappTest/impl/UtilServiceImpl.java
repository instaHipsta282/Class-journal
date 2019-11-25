package com.instahipsta.webappTest.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.domain.dto.CaptchaResponseDto;
import com.instahipsta.webappTest.services.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class UtilServiceImpl implements UtilService {

    private static final String CAPTCHA_URL = "https://www.google.com/recaptcha/api/siteverify?secret=%s&response=%s";


    @Value("${recaptcha.secret}")
    private String secret;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    ObjectMapper myObjectMapper = new ObjectMapper();

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

    @Override
    public boolean checkPassword(String password, User user, Model model) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("oldPasswordError", "The password is failed");
            model.addAttribute("passwordError", "The password is failed");
            return false;
        }
        else return true;
    }

    @Override
    public boolean checkNewPassword(String newPassword, String newPasswordRe, String password, Model model) {
            if (!newPassword.equals(newPasswordRe)) {
                model.addAttribute("newPasswordError",
                        "The new password and new repeated password fields are different");
                model.addAttribute("passwordError", "Passwords are different!");
            }
            else if (password != null && newPassword.equals(password)) {
                model.addAttribute("newPasswordError", "Your new password cannot be equals with old password");
            }
            else return true;

            return false;
    }

    @Override
    public boolean checkNewEmail(User user, String newEmail, Model model) {
        if (user.getEmail().equals(newEmail)) {
            model.addAttribute("newEmailError", "You are already using this email");
            return false;
        }
        return true;
    }

    @Override
    public boolean checkNewPhone(User user, String newPhone, Model model) {
        if (newPhone.equals(user.getPhone())) {
            model.addAttribute("oldPhoneError", "The phone numbers cannot be equals");
            return false;
        }
        return true;
    }

    @Override
    public boolean captchaCheck(String url, Model model) {
        CaptchaResponseDto response = restTemplate
                .postForObject(formCaptcha(url), Collections.emptyList(), CaptchaResponseDto.class);
        if (response != null && !response.isSuccess()) {
            model.addAttribute("captchaError", "Fill captcha");
            return false;
        }
        return true;
    }

    @Override
    public String formCaptcha(String captchaResponse) {
        return String.format(CAPTCHA_URL, secret, captchaResponse);
    }

    @Override
    public String objectToJson(Object object) {
        String json = null;
        try { json = myObjectMapper.writeValueAsString(object); }
        catch (JsonProcessingException e) { e.printStackTrace(); }

        return json;
    }

    @Override
    public Double percentCounter(int fullNumber, int partOfNumber) {
        double onePercent = ((double) fullNumber / 100);
        return partOfNumber / onePercent;
    }

    @Override
    public String doubleToString(double number, int decimalPlaces) {
        return String.format("%." + decimalPlaces + "f", number).replace(',', '.');
    }

    @Override
    public String doubleToString(double number) {
        return String.format("%.1f", number).replace(',', '.');
    }
}
