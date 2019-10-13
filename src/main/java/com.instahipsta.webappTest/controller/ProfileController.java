package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.Schedule;
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
    private UserServiceImpl userServiceImpl;

    @Autowired
    private CourseServiceImpl courseServiceImpl;

    @Autowired
    private ScheduleServiceImpl scheduleService;


    @GetMapping("/profile")
    public String getProfileForm(@AuthenticationPrincipal User user,
            Model model) {

        Set<Course> courses = courseServiceImpl.findAvailableCourses();
        courses.removeAll(courseServiceImpl.findActuallyCoursesByUserId(user.getId()));

        model.addAttribute("userCourses", courseServiceImpl.findActuallyCoursesByUserId(user.getId()));

        model.addAttribute("courses", courses);

        return "profile";
    }


    @PostMapping("/changePassword")
    public String changePassword(
                               @AuthenticationPrincipal User user,
                               Model model,
                               @RequestParam String oldPassword,
                               @RequestParam String newPassword,
                               @RequestParam String newPasswordRe
    ) {

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

            userServiceImpl.updateEmail(user, newEmail);
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

            System.out.println(firstName + " " + secondName + " " + lastName);
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

        model.addAttribute("userCourses", courseServiceImpl.findActuallyCoursesByUserId(currentUser.getId()));

        Set<Course> diff = courseServiceImpl.findAvailableCourses();
        diff.removeAll(courseServiceImpl.findActuallyCoursesByUserId(currentUser.getId()));

        model.addAttribute("courses", diff);

        for (Course course : diff) {
            if (form.containsKey(course.getId().toString())) {
                currentUser.getCourses().add(course);
                scheduleFactory(currentUser, course);
            }


        }

        userRepo.save(currentUser);

        model.addAttribute("userCourses", courseServiceImpl.findActuallyCoursesByUserId(currentUser.getId()));

        diff.removeAll(courseServiceImpl.findActuallyCoursesByUserId(currentUser.getId()));

        model.addAttribute("courses", diff);

        return "profile";
    }


    private void scheduleFactory(User student, Course course) {
        for (int i = 0; i < course.getDaysCount(); i++) {
            Schedule schedule = new Schedule();

            schedule.setDate(course.getStartDate().plusDays(i));
            schedule.setStudent(student);
            schedule.setCourse(course);

            scheduleService.addSchedule(schedule);
        }
    }

}



