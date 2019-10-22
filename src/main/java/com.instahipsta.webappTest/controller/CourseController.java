package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.PresenceStatus;
import com.instahipsta.webappTest.domain.Schedule;
import com.instahipsta.webappTest.domain.Score;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.ScheduleServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
@RequestMapping("/courseList")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping
    public String courseList(Model model) {
        model.addAttribute("courses", courseService.findAvailableCourses());
        return "courseList";
    }

    @GetMapping("{course}")
    public String getCourse(@PathVariable Course course, Model model) {
        model.addAttribute("course", course);
        model.addAttribute("users", userServiceImpl.findUsersByCourseId(course.getId()));

        Map<User, Set<Schedule>> usersSchedule = new HashMap<>();

        for(User user : userServiceImpl.findUsersByCourseId(course.getId())) {
            usersSchedule.put(user, scheduleService.getScheduleByUserAndCourseId(user.getId(), course.getId()));
        }

        List<LocalDate> scheduleDays = new ArrayList<>();

        LocalDate startDate =  Instant.ofEpochMilli(courseService.getStartDateById(course.getId()).getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate =  Instant.ofEpochMilli(courseService.getEndDateById(course.getId()).getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();

        while (startDate.isBefore(endDate)) {
            scheduleDays.add(startDate);
            startDate = startDate.plusDays(1);
        }

        Collections.sort(scheduleDays);
        model.addAttribute("usersSchedule", usersSchedule);
        model.addAttribute("scheduleDays", scheduleDays);
        model.addAttribute("presenceStatuses", PresenceStatus.values());
        model.addAttribute("scores", Score.values());

        return "course";
    }

    @PostMapping("addNewCourse")
    public String addNewCourse(Model model,
                               @RequestParam String courseTitle,
                               @RequestParam(required = false) String courseDescription,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                               @RequestParam MultipartFile courseImage) {

        Course course = new Course();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate newStartDate = LocalDate.parse(startDate, formatter);
        LocalDate newEndDate = LocalDate.parse(endDate, formatter);

        course.setTitle(courseTitle);
        course.setStartDate(newStartDate);
        course.setEndDate(newEndDate);
        course.setStudentsCount(0);
        course.setDaysCount((int)ChronoUnit.DAYS.between(newStartDate, newEndDate));

        if (!courseImage.isEmpty()) {
            String imageName = controllerUtils.saveFile(courseImage);
            course.setImage(imageName);
        }

        if (!courseDescription.isEmpty()) {
            course.setDescription(courseDescription);
        }

        if (!courseService.doesThisCourseExist(courseTitle, newStartDate, newEndDate)) {
            courseService.addCourse(course);
        }
        else model.addAttribute("courseTitleError", "This course already exists");



        model.addAttribute("courses", courseService.findAvailableCourses());

        return "courseList";
    }

    @GetMapping("deleteCourse")
    public String deleteCourse(Model model,
                               @RequestParam("courseId") Course course) {

        courseService.deleteCourse(course);

        model.addAttribute("courses", courseService.findAvailableCourses());

        return "courseList";
    }

    @PostMapping("{course}/changeSchedule")
    public String changeSchedule(@PathVariable Course course,
                                 @RequestParam User user,
                                 @RequestParam String date,
                                 @RequestParam(required = false) PresenceStatus presenceStatus,
                                 @RequestParam(required = false) Score score,
                                 Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH);
        LocalDate scheduleDate = LocalDate.parse(date, formatter);

        Schedule schedule = scheduleService.getScheduleByDateUserAndCourseId(scheduleDate, user.getId(), course.getId());
        schedule.setPresenceStatus(presenceStatus);
        schedule.setScore(score);

        scheduleService.save(schedule);

        return "redirect:/courseList/{course}";
    }
}
