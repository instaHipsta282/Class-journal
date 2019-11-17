package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.PresenceStatus;
import com.instahipsta.webappTest.domain.Schedule;
import com.instahipsta.webappTest.domain.Score;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.ScheduleServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/courseList")
public class CourseController {

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping
    public String courseList(Model model) {
        model.addAttribute("courses", courseService.findActuallyCourses());
        return "courseList";
    }

    @GetMapping("{course}")
    public String getCourse(@PathVariable Course course, Model model) {
        model.addAttribute("course", course);
        model.addAttribute("users", userService.findUsersByCourseId(course.getId()));

        Map<User, Set<Schedule>> usersSchedule = new HashMap<>();

        for (User user : userService.findUsersByCourseId(course.getId())) {
            usersSchedule.put(user, scheduleService.getScheduleByUserAndCourseId(user.getId(), course.getId()));
        }

        List<LocalDate> scheduleDays = new ArrayList<>();

        LocalDate startDate = Instant.ofEpochMilli(courseService.getStartDateById(course.getId()).getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endDate = Instant.ofEpochMilli(courseService.getEndDateById(course.getId()).getTime())
                .atZone(ZoneId.systemDefault()).toLocalDate();

        while (startDate.isBefore(endDate)) {
            scheduleDays.add(startDate);
            startDate = startDate.plusDays(1);
        }

        List<User> usersForCourse = userService
                .findAll()
                .stream()
                .filter(u -> !course.getStudents().contains(u))
                .collect(Collectors.toList());

        Collections.sort(scheduleDays);
        model.addAttribute("usersSchedule", usersSchedule);
        model.addAttribute("scheduleDays", scheduleDays);
        model.addAttribute("presenceStatuses", PresenceStatus.values());
        model.addAttribute("scores", Score.values());
        model.addAttribute("usersForCourse", usersForCourse);
        model.addAttribute("newStudentExist", usersForCourse.size() > 0);

        return "course";
    }

    @PostMapping("addNewCourse")
    public String addNewCourse(Model model,
                               @RequestParam String courseTitle,
                               @RequestParam(required = false) String courseDescription,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                               @RequestParam Integer studentsLimit,
                               @RequestParam MultipartFile courseImage) {

        Course course = new Course();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate newStartDate = LocalDate.parse(startDate, formatter);
        LocalDate newEndDate = LocalDate.parse(endDate, formatter);

        course.setTitle(courseTitle);
        course.setStartDate(newStartDate);
        course.setEndDate(newEndDate);
        course.setStudentsCount(0);
        if (studentsLimit != null) course.setStudentsLimit(studentsLimit);
        course.setDaysCount((int) ChronoUnit.DAYS.between(newStartDate, newEndDate));

        if (!courseImage.isEmpty()) {
            String imageName = controllerUtils.saveFile(courseImage);
            course.setImage(imageName);
        }

        if (!courseDescription.isEmpty()) {
            course.setDescription(courseDescription);
        }

        if (!courseService.doesThisCourseExist(courseTitle, newStartDate, newEndDate)) {
            courseService.save(course);
        } else model.addAttribute("courseTitleError", "This course already exists");


        model.addAttribute("courses", courseService.findActuallyCourses());

        return "courseList";
    }

    @Transactional
    @GetMapping("deleteCourse")
    public String deleteCourse(@RequestParam("courseId") Course course,
                               Model model) {

        scheduleService.deleteAllScheduleForCourse(course.getId());
        courseService.deleteCourse(course);

        return "redirect:/courseList";
    }

    @PostMapping("{course}/changeSchedule")
    public String changeSchedule(@PathVariable Course course,
                                 @RequestParam User user,
                                 @RequestParam String date,
                                 @RequestParam(required = false) PresenceStatus presenceStatus,
                                 @RequestParam(required = false) Score score,
                                 Model model) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate scheduleDate = LocalDate.parse(date, formatter);

        Schedule schedule = scheduleService
                .getScheduleByDateUserAndCourseId(scheduleDate, user.getId(), course.getId());
        schedule.setPresenceStatus(presenceStatus);
        schedule.setScore(score);

        scheduleService.save(schedule);

        return "redirect:/courseList/{course}";
    }

    @PostMapping("{course}/addUser")
    public String addUser(@PathVariable Course course,
                          @RequestParam(required = false) String usersId,
                          @RequestParam(required = false) String userName,
                          Model model) {
        if (usersId != null) {
            userService.getUserListFromStringWithIds(usersId)
                    .forEach(user -> courseService.addUserToCourse(course, user));
        } else if (userName != null) {
            try {
                List<User> users = userService.getUserListFromStringWithName(userName);
                if (users.size() > 1) model.addAttribute("typeError", "Ambiguous name");
                else if (users.size() == 0) model.addAttribute("typeError", "User doesnt exist");
                else {
                    courseService.addUserToCourse(course, users.get(0));
                }
            } catch (WrongNumberArgsException e) {
                model.addAttribute("typeError", "Wrong name format");
                e.getMessage();
            }
        }
        return "redirect:/courseList/{course}";
    }

    @Transactional
    @GetMapping("{course}/deleteUser")
    public String deleteUserFromCourse(@PathVariable Course course,
                                       @RequestParam String userId,
                                       Model model) {

        userService.getUserListFromStringWithIds(userId)
                .forEach(user -> {
                    userService.deleteUserFromCourse(course, user);
                    scheduleService.deleteCourseScheduleForUser(course.getId(), user.getId());
                });

        return "redirect:/courseList/{course}";
    }

}
