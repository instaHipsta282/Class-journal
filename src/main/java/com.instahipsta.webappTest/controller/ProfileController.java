package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.ScheduleServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import com.instahipsta.webappTest.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
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
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    //testing
    @GetMapping
    public String getProfile(@AuthenticationPrincipal User user,
                             Model model) {
        courseService.addDataToModel(user, model);

        return "profile";
    }

    //testing
    @PostMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal User user,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String newPasswordRe,
                                 Model model) {
        courseService.addDataToModel(user, model);

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

    //testing
    @PostMapping("/changeEmail")
    public String changeEmail(@AuthenticationPrincipal User user,
                              @RequestParam String newEmail,
                              @RequestParam String password,
                              Model model) {
        courseService.addDataToModel(user, model);

        //If user have some error in email edit form, form don`t collapse
        model.addAttribute("someEmailError", "You have some email error");

        boolean isPasswordOk = checkPassword(password, user, model);
        boolean isNewEmailOk = checkNewEmail(user, newEmail, model);

        if (isPasswordOk && isNewEmailOk) {
            model.addAttribute("someEmailError", null);
            userService.changeEmail(user, newEmail);
        }
        return "profile";
    }

    //testing
    @PostMapping("/changePhone")
    public String changePhone(@AuthenticationPrincipal User user,
                              @RequestParam String newPhone,
                              @RequestParam String password,
                              Model model) {
        courseService.addDataToModel(user, model);

        //If user have some error in email edit form, form don`t collapse
        model.addAttribute("somePhoneError", "You have some phone error");

        boolean isPasswordOk = checkPassword(password, user, model);
        boolean isNewPhoneOk = checkNewPhone(user, newPhone, model);

        if (isPasswordOk && isNewPhoneOk) {
            model.addAttribute("somePhoneError", null);
            userService.changePhone(user, newPhone);
        }

        return "profile";
    }

    @PostMapping("/changeName")
    public String changeName(
            @AuthenticationPrincipal User user,
            Model model,
            @RequestParam String firstName,
            @RequestParam String secondName,
            @RequestParam String lastName,
            @RequestParam String password
    ) {

        boolean isPasswordTrue = passwordEncoder.matches(password, user.getPassword());

        //If user have some error in name edit form, form don`t collapse
        model.addAttribute("someNameError", "You have some name error");

        if (password.isEmpty()) {
            model.addAttribute("passwordError", "The password field cannot be empty");
        }

        if (!isPasswordTrue) {
            model.addAttribute("passwordError", "The password is failed");
        }

        if (!password.isEmpty() && isPasswordTrue) {

            model.addAttribute("someNameError", null);

            if (!firstName.isEmpty()) user.setFirstName(firstName);
            if (!secondName.isEmpty()) user.setSecondName(secondName);
            if (!lastName.isEmpty()) user.setLastName(lastName);

            userRepo.save(user);
        }
        return "profile";
    }

    @PostMapping("/addCourse")
    public String addCourse(@AuthenticationPrincipal User currentUser,
                            @RequestParam Map<String, String> form,
                            Model model) {

        model.addAttribute("userCourses", courseService.findActuallyCoursesByUserId(currentUser.getId()));

        Set<Course> diff = courseService.findActuallyCourses();
        diff.removeAll(courseService.findActuallyCoursesByUserId(currentUser.getId()));

        model.addAttribute("courses", diff);

        for (Course course : diff) {
            if (form.containsKey(course.getId().toString())) {
                currentUser.getCourses().add(course);

                scheduleService.scheduleFactory(currentUser, course);

                course.setStudentsCount(course.getStudentsCount() + 1);
            }
        }

        userRepo.save(currentUser);

        model.addAttribute("userCourses", courseService.findActuallyCoursesByUserId(currentUser.getId()));

        diff.removeAll(courseService.findActuallyCoursesByUserId(currentUser.getId()));

        model.addAttribute("courses", diff);

        return "redirect:/profile";
    }

    private boolean checkPassword(String password, User user, Model model) {
        if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("oldPasswordError", "The password is failed");
            model.addAttribute("passwordError", "The password is failed");
        }
        else return true;

        return false;
    }

    private boolean checkNewPassword(String newPassword, String newPasswordRe, String password, Model model) {
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

    private boolean checkNewEmail(User user, String newEmail, Model model) {
        boolean check = true;

        if (user.getEmail().equals(newEmail)) {
            check = false;
            model.addAttribute("newEmailError", "You are already using this email");
        }
        return check;
    }

    private boolean checkNewPhone(User user, String newPhone, Model model) {
        boolean check = true;

        if (newPhone.equals(user.getPhone())) {
            check = false;
            model.addAttribute("oldPhoneError", "The phone numbers cannot be equals");
        }
        return check;
    }


}



