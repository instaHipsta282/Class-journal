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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import java.util.Set;

@Controller
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


    @GetMapping("/profile")
    public String getProfile(@AuthenticationPrincipal User user,
                             Model model) {
        loadDataForProfile(user, model);

        return "profile";
    }

    @PostMapping("/changePassword")
    public String changePassword(@AuthenticationPrincipal User user,
                                 @RequestParam String oldPassword,
                                 @RequestParam String newPassword,
                                 @RequestParam String newPasswordRe,
                                 Model model) {
        loadDataForProfile(user, model);
        //If user have some error in password edit form, form don`t collapse
        model.addAttribute("somePasswordError", "You have some password error");

        System.out.println("Start changing: " + oldPassword + " " + newPassword);

        boolean oldPasswordOk = checkPassword(oldPassword, user, model);
        boolean newPasswordOk = checkNewPassword(newPassword, newPasswordRe, oldPassword, model);

        if (oldPasswordOk && newPasswordOk) {
            model.addAttribute("somePasswordError", null);
            System.out.println("Password are correct: " + oldPassword + newPassword);
            userService.changePassword(user, newPassword);
        }

        return "profile";
    }


    @PostMapping("/changeEmail")
    public String changeEmail(@AuthenticationPrincipal User user,
                              Model model,
                              @RequestParam String oldEmail,
                              @RequestParam String newEmail,
                              @RequestParam String password
    ) {

        boolean isOldEmailTrue = user.getEmail().equals(oldEmail);
        boolean isPasswordTrue = passwordEncoder.matches(password, user.getPassword());
        boolean isEmailsEquals = newEmail.equals(oldEmail);

        //If user have some error in email edit form, form don`t collapse
        model.addAttribute("someEmailError", "You have some email error");

        if (oldEmail.isEmpty()) {
            model.addAttribute("oldEmailError", "The old email field cannot be empty");
        }

        if (newEmail.isEmpty()) {
            model.addAttribute("newEmailError", "The new email field cannot be empty");
        }

        if (password.isEmpty()) {
            model.addAttribute("passwordError", "The password field cannot be empty");
        }
        if (isEmailsEquals) {
            model.addAttribute("oldEmailError", "The emails cannot be equals");
        }
        if (!isPasswordTrue) {
            model.addAttribute("passwordError", "The password is failed");
        }
        if (!isOldEmailTrue) {
            model.addAttribute("oldEmailError", "The email is failed");
        }

        if (!oldEmail.isEmpty() && !newEmail.isEmpty() && !password.isEmpty() && isOldEmailTrue && isPasswordTrue && !isEmailsEquals) {

            model.addAttribute("someEmailError", null);

            userService.changeEmail(user, newEmail);
        }

        return "profile";
    }

    @PostMapping("/changePhone")
    public String changePhone(@AuthenticationPrincipal User user,
                              Model model,
                              @RequestParam String oldPhone,
                              @RequestParam String newPhone,
                              @RequestParam String password
    ) {

        boolean isOldPhoneTrue = user.getPhone().equals(oldPhone);
        boolean isPasswordTrue = passwordEncoder.matches(password, user.getPassword());
        boolean isPhonesEquals = newPhone.equals(oldPhone);

        //If user have some error in email edit form, form don`t collapse
        model.addAttribute("somePhoneError", "You have some phone error");

        if (oldPhone.isEmpty()) {
            model.addAttribute("oldPhoneError", "The old phone number field cannot be empty");
        }

        if (newPhone.isEmpty()) {
            model.addAttribute("newPhoneError", "The new phone number field cannot be empty");
        }

        if (password.isEmpty()) {
            model.addAttribute("passwordError", "The password field cannot be empty");
        }
        if (isPhonesEquals) {
            model.addAttribute("oldPhoneError", "The phone numbers cannot be equals");
        }
        if (!isPasswordTrue) {
            model.addAttribute("passwordError", "The password is failed");
        }
        if (!isOldPhoneTrue) {
            model.addAttribute("oldPhoneError", "The phone number is failed");
        }

        if (!oldPhone.isEmpty() && !newPhone.isEmpty() && !password.isEmpty() && isOldPhoneTrue && isPasswordTrue && !isPhonesEquals) {

            model.addAttribute("somePhoneError", null);

            user.setPhone(newPhone);
            userRepo.save(user);
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

    private void loadDataForProfile(User user, Model model) {
        Set<Course> courses = courseService.findActuallyCourses();
        courses.removeAll(courseService.findActuallyCoursesByUserId(user.getId()));

        model.addAttribute("currentUser", user);
        model.addAttribute("userCourses", courseService.findActuallyCoursesByUserId(user.getId()));
        model.addAttribute("courses", courses);

    }


    private boolean checkPassword(String password, User user, Model model) {
        if (password.isEmpty()) {
            model.addAttribute("oldPasswordError", "The old password field cannot be empty");
        }
        else if (!passwordEncoder.matches(password, user.getPassword())) {
            model.addAttribute("oldPasswordError", "The password is failed");
        }
        else return true;

        return false;
    }

    private boolean checkNewPassword(String newPassword, String newPasswordRe, String password, Model model) {
        if (newPassword.isEmpty()) {
            model.addAttribute("newPasswordError", "The new password field cannot be empty");
        }
        else if (newPasswordRe.isEmpty()) {
            model.addAttribute("newPasswordReError", "The repeated new password field cannot be empty");
        }
        else if (!newPassword.equals(newPasswordRe)) {
            model.addAttribute("newPasswordError",
                    "The new password and new repeated password fields are different");
        }
        else if (newPassword.equals(password)) {
                model.addAttribute("newPasswordError", "Your new password cannot be equals with old password");
        }
        else return true;

        return false;
    }

}



