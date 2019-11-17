package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/userList")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "userList";
    }

    @PostMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal User user,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String newPasswordRe,
                                 Model model) {
        //If user have some error in password edit form, form don`t collapse
        model.addAttribute("somePasswordError", "You have some password error");

        boolean oldPasswordOk = checkPassword(oldPassword, user, model);
        boolean newPasswordOk = checkNewPassword(newPassword, newPasswordRe, oldPassword, model);

        if (oldPasswordOk && newPasswordOk) {
            model.addAttribute("somePasswordError", null);
            userService.changePassword(user, newPassword);
        }

        return "profile";
    }

    @PostMapping("/changeEmail")
    public String changeEmail(@AuthenticationPrincipal User user,
                              @RequestParam String oldEmail,
                              @RequestParam String newEmail,
                              @RequestParam String password,
                              Model model) {
        boolean isPasswordOk = checkPassword(password, user, model);
        boolean isEmailOk = checkEmail(oldEmail, user, model);
        boolean isNewEmailOk = checkNewEmail(user, oldEmail, newEmail, model);

        //If user have some error in email edit form, form don`t collapse
        model.addAttribute("someEmailError", "You have some email error");

        if (isPasswordOk && isEmailOk && isNewEmailOk) {
            model.addAttribute("someEmailError", null);
            userService.changeEmail(user, newEmail);
        }

        return "profile";
    }

    @PostMapping("/changePhone")
    public String changePhone(@AuthenticationPrincipal User user,
                              @RequestParam String oldPhone,
                              @RequestParam String newPhone,
                              @RequestParam String password,
                              Model model) {
        boolean isPasswordOk = checkPassword(password, user, model);
        boolean isPhoneOk = checkPhone(user, oldPhone, model);
        boolean isNewPhoneOk = checkNewPhone(user, oldPhone, newPhone, model);

        //If user have some error in email edit form, form don`t collapse
        model.addAttribute("somePhoneError", "You have some phone error");

        if (isPasswordOk && isPhoneOk && isNewPhoneOk) {
            model.addAttribute("somePhoneError", null);

            userService.changePhone(user, newPhone);
        }

        return "profile";
    }

    @Transactional
    @PostMapping("/changeName")
    public String changeName(@AuthenticationPrincipal User user,
                             @RequestParam String firstName,
                             @RequestParam String secondName,
                             @RequestParam String lastName) {
            if (!firstName.isEmpty()) userService.changeFirstName(user, firstName);
            if (!secondName.isEmpty()) userService.changeSecondName(user, secondName);
            if (!lastName.isEmpty()) userService.changeLastName(user, lastName);

        return "profile";
    }

    @PostMapping("/addCourse")
    public String addCourse(@AuthenticationPrincipal User currentUser,
                            @RequestParam Map<String, String> form,
                            Model model) {
//
//        model.addAttribute("userCourses", courseService.findActuallyCoursesByUserId(currentUser.getId()));

        Set<Course> availableCourses = courseService.findAvailableCoursesForUser(currentUser);
        addCourseFromForm(currentUser, availableCourses, form);
//        model.addAttribute("courses", availableCourses);
        model.addAttribute("userCourses", courseService.findActuallyCoursesByUserId(currentUser.getId()));
        availableCourses.removeAll(courseService.findActuallyCoursesByUserId(currentUser.getId()));
        model.addAttribute("courses", availableCourses);

        return "redirect:/profile";
    }

    private void addCourseFromForm(User user, Set<Course> availableCourses, Map<String, String> form) {
        availableCourses.stream()
                .filter(course -> form.containsKey(course.getId().toString()))
                .forEach(course -> courseService.addUserToCourse(course, user));
    }

    private boolean checkNewEmail(User user, String oldEmail, String newEmail, Model model) {
        boolean check = true;

        if (newEmail.isEmpty()) {
            check = false;
            model.addAttribute("newEmailError", "The new email field cannot be empty");
        }
        if (newEmail.equals(oldEmail)) {
            check = false;
            model.addAttribute("oldEmailError", "The emails cannot be equals");
        }
        return check;
    }

    private boolean checkPassword(String password, User user, Model model) {
        boolean check = true;

        boolean isOldPasswordTrue = passwordEncoder.matches(password, user.getPassword());

        if (password.isEmpty()) {
            check = false;
            model.addAttribute("oldPasswordError", "The old password field cannot be empty");
        }
        if (!password.isEmpty() && !isOldPasswordTrue) {
            check = false;
            model.addAttribute("oldPasswordError", "The password is failed");
        }
        return check;
    }

    private boolean checkNewPassword(String newPassword, String newPasswordRe, String password, Model model) {
        boolean check = true;

        if (newPassword.isEmpty()) {
            check = false;
            model.addAttribute("newPasswordError", "The new password field cannot be empty");
        }
        if (newPasswordRe.isEmpty()) {
            check = false;
            model.addAttribute("newPasswordReError", "The repeated new password field cannot be empty");
        }
        if (!newPasswordRe.isEmpty() && !newPassword.isEmpty()) {
            if (!newPassword.equals(newPasswordRe)) {
                check = false;
                model.addAttribute("newPasswordError",
                        "The new password and new repeated password fields are different");
            }
            if (newPassword.equals(password)) {
                check = false;
                model.addAttribute("newPasswordError", "Your new password cannot be equals with old password");
            }
        }
        return check;
    }

    private boolean checkEmail(String email, User user, Model model) {
        boolean check = true;

        if (!user.getEmail().equals(email)) {
            check = false;
            model.addAttribute("oldEmailError", "The email is failed");
        }
        if (email.isEmpty()) {
            check = false;
            model.addAttribute("oldEmailError", "The old email field cannot be empty");
        }

        return check;
    }

    private boolean checkPhone(User user, String phone, Model model) {
        boolean check = true;
        if (phone.isEmpty()) {
            check = false;
            model.addAttribute("oldPhoneError", "The old phone number field cannot be empty");
        }
        if (!user.getPhone().equals(phone)) {
            check = false;
            model.addAttribute("oldPhoneError", "The phone number is failed");
        }
        return check;
    }

    private boolean checkNewPhone(User user, String phone, String newPhone, Model model) {
        boolean check = true;

        if (newPhone.isEmpty()) {
            check = false;
            model.addAttribute("newPhoneError", "The new phone number field cannot be empty");
        }
        if (newPhone.equals(phone)) {
            check = false;
            model.addAttribute("oldPhoneError", "The phone numbers cannot be equals");
        }
        return check;
    }

}
