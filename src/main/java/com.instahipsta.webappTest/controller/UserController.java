package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.ScheduleServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import com.instahipsta.webappTest.impl.UtilServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/userList")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private UtilServiceImpl utilService;

    @GetMapping
    public String getUserList(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/userList";
    }

    @GetMapping("{user}")
    public String getUserPage(Model model,
                              @PathVariable User user) {
        Map<Course, String> courses = new HashMap<>();
        for (Course course : user.getCourses()) {
            double score = userService.averageScore(scheduleService.findScoreByUserIdAndCourseId(user, course));
            courses.put(course, utilService.doubleToString(score, 1));
        }
        model.addAttribute("currentUser", user);
        model.addAttribute("courses", courses);
        return "user";
    }

}
