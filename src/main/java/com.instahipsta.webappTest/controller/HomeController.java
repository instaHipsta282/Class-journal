package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {

    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping("/")
    public String getHomePage(Model model) {

        model.addAttribute("passCourses", courseService.findPassCourses());
        model.addAttribute("futureCourses", courseService.findFutureCourses());
        model.addAttribute("presentCourses", courseService.findPresentCourses());
        model.addAttribute("actuallyCoursesPercent", courseService.actuallyCoursesWithPercent());

        return "home";
    }
}