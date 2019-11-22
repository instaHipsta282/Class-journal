package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.FileServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import com.instahipsta.webappTest.impl.UtilServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UtilServiceImpl utilService;

    @Autowired
    private FileServiceImpl fileService;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/registration")
    public String getRegistrationPage() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!auth.getAuthorities().toString().contains("ROLE_ANONYMOUS")) return "redirect:/";

        return "registration";
    }

    //now i dont know how testing it
    @PostMapping("/registration")
    public String registration(@RequestParam("g-recaptcha-response") String captchaResponse,
                          @RequestParam("password2") String passwordConfirm,
                          @RequestParam(required = false) MultipartFile usersPhoto,
                          @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        boolean isCaptchaOk = utilService.captchaCheck(captchaResponse, model);
        boolean isPasswordOk = utilService.checkNewPassword(user.getPassword(), passwordConfirm, null, model);
        boolean isEmailAlreadyUse = userService.isEmailAlreadyUse(user.getEmail());

        if (isEmailAlreadyUse) model.addAttribute("emailError", "Email address is already use");

        if (!isPasswordOk || bindingResult.hasErrors() || !isCaptchaOk || isEmailAlreadyUse) {
            Map<String, String> errors = utilService.getErrors(bindingResult);
            model.mergeAttributes(errors);
            return "registration";
        }

        String resultFileName = fileService.saveFile(usersPhoto);
        user.setPhoto(resultFileName);

        if (!userService.addUser(user)) {
            model.addAttribute("usernameError", "User exist!");
            return "registration";
        }
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("messageType", "success");
            model.addAttribute("message", "User successfully activated");
        } else {
            model.addAttribute("messageType", "danger");
            model.addAttribute("message", "Activation code is not found");
        }
        return "redirect:/login";
    }
}
