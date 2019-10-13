package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/courseList")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping
    public String courseList(Model model) {
        model.addAttribute("courses", courseServiceImpl.findAvailableCourses());
        return "courseList";
    }

    @GetMapping("{course}")
    public String getCourse(@PathVariable Course course, Model model) {
        model.addAttribute("course", course);
        model.addAttribute("users", userServiceImpl.findUsersByCourseId(course.getId()));

        return "course";
    }

    @PostMapping("addNewCourse")
    public String addNewCourse(Model model,
                            @RequestParam String courseTitle,
                            @RequestParam String startDate,
                            @RequestParam String endDate) {

        Course course = new Course();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate newStartDate = LocalDate.parse(startDate, formatter);
        LocalDate newEndDate = LocalDate.parse(endDate, formatter);

        course.setTitle(courseTitle);
        course.setStartDate(newStartDate);
        course.setEndDate(newEndDate);
        course.setStudentsCount(0);
        course.setDaysCount((int)ChronoUnit.DAYS.between(newStartDate, newEndDate));

        try {
            courseServiceImpl.addCourse(course);
        }
        catch (RuntimeException e) {
            Throwable rootCause = com.google.common.base.Throwables.getRootCause(e);
            if (rootCause instanceof SQLException) {
                if ("23505".equals(((SQLException) rootCause).getSQLState())) {
                    model.addAttribute("courseTitleError", "This course already exists");
                }
            }
        }
        model.addAttribute("courses", courseServiceImpl.findAvailableCourses());

        return "courseList";
    }

    @GetMapping("deleteCourse")
    public String deleteCourse(Model model,
                               @RequestParam("courseId") Course course) {

        courseServiceImpl.deleteCourse(course);

        model.addAttribute("courses", courseServiceImpl.findAvailableCourses());

        return "courseList";
    }


}
