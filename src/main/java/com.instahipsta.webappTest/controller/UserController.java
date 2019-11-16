package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Set;

@Controller
@RequestMapping("/userList")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", userService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String getUser(@PathVariable String user,
                          Model model) {

        User currentUser = userService.getUserListFromStringWithIds(user).get(0);
        Set<Course> courses = courseService.findActuallyCourses();
        courses.removeAll(courseService.findActuallyCoursesByUserId(currentUser.getId()));

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userCourses", courseService.findActuallyCoursesByUserId(currentUser.getId()));
        model.addAttribute("courses", courses);

        return "profile";
    }
}
