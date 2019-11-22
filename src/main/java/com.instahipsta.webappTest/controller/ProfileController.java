package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import com.instahipsta.webappTest.impl.UtilServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private UtilServiceImpl utilService;

    @GetMapping
    public String getProfile(@AuthenticationPrincipal User user,
                             Model model) {
        courseService.addDataToModel(user, model);
        return "profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal User user,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String newPasswordRe,
                                 Model model) {
        courseService.addDataToModel(user, model);

        //If user have some error in password edit form, form don`t collapse
        model.addAttribute("somePasswordError", "You have some password error");

        boolean oldPasswordOk = utilService.checkPassword(oldPassword, user, model);
        boolean newPasswordOk = utilService.checkNewPassword(newPassword, newPasswordRe, oldPassword, model);

        if (oldPasswordOk && newPasswordOk) {
            model.addAttribute("somePasswordError", null);
            userService.changePassword(user, newPassword);
        }
        return "profile";
    }

    @PostMapping("/changeEmail")
    public String changeEmail(@AuthenticationPrincipal User user,
                              @RequestParam String newEmail,
                              @RequestParam String password,
                              Model model) {
        courseService.addDataToModel(user, model);

        //If user have some error in email edit form, form don`t collapse
        model.addAttribute("someEmailError", "You have some email error");

        boolean isPasswordOk = utilService.checkPassword(password, user, model);
        boolean isNewEmailOk = utilService.checkNewEmail(user, newEmail, model);

        if (isPasswordOk && isNewEmailOk) {
            model.addAttribute("someEmailError", null);
            userService.changeEmail(user, newEmail);
        }
        return "profile";
    }

    @PostMapping("/changePhone")
    public String changePhone(@AuthenticationPrincipal User user,
                              @RequestParam String newPhone,
                              @RequestParam String password,
                              Model model) {
        courseService.addDataToModel(user, model);

        //If user have some error in email edit form, form don`t collapse
        model.addAttribute("somePhoneError", "You have some phone error");

        boolean isPasswordOk = utilService.checkPassword(password, user, model);
        boolean isNewPhoneOk = utilService.checkNewPhone(user, newPhone, model);

        if (isPasswordOk && isNewPhoneOk) {
            model.addAttribute("somePhoneError", null);
            userService.changePhone(user, newPhone);
        }

        return "profile";
    }

    @Transactional
    @PostMapping("/changeName")
    public String changeName(@AuthenticationPrincipal User user,
                             @RequestParam(required = false) String firstName,
                             @RequestParam(required = false) String secondName,
                             @RequestParam(required = false) String lastName,
                             Model model) {
        courseService.addDataToModel(user, model);

        if (firstName != null) userService.changeFirstName(user, firstName);
        if (secondName != null) userService.changeSecondName(user, secondName);
        if (lastName != null) userService.changeLastName(user, lastName);

        return "profile";
    }

    @PostMapping("/addCourse")
    public String addCourse(@AuthenticationPrincipal User currentUser,
                            @RequestParam Map<String, String> form,
                            Model model) {
        Set<Course> availableCourses = courseService.findAvailableCoursesForUser(currentUser);
        courseService.addCourseFromForm(currentUser, availableCourses, form);

        model.addAttribute("userCourses", courseService.findActuallyCoursesByUserId(currentUser.getId()));

        availableCourses.removeAll(courseService.findActuallyCoursesByUserId(currentUser.getId()));

        model.addAttribute("courses", availableCourses);

        return "redirect:/profile";
    }
}