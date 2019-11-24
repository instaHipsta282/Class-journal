package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.*;
import com.instahipsta.webappTest.impl.*;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    private FileServiceImpl fileService;

    @Autowired
    private UtilServiceImpl utilService;

    @GetMapping
    public String courseList(Model model) {
        model.addAttribute("courses", courseService.findActuallyCourses());
        return "courseList";
    }

    @GetMapping("{course}")
    public String getCourse(@PathVariable Course course, Model model) {
        Map<User, Set<Schedule>> usersSchedule = userService.findUsersSchedule(course);

        List<LocalDate> scheduleDays = courseService.getScheduleDays(course.getStartDate(), course.getEndDate());
        Collections.sort(scheduleDays);

        List<User> usersForCourse = courseService.getNewUsersForCourse(course);

        model.addAttribute("usersSchedule", usersSchedule);
        model.addAttribute("scheduleDays", scheduleDays);
        model.addAttribute("presenceStatuses", PresenceStatus.values());
        model.addAttribute("scores", Score.values());
        model.addAttribute("usersForCourse", usersForCourse);
        model.addAttribute("newStudentExist", usersForCourse.size() > 0);
        model.addAttribute("course", course);
        model.addAttribute("users", userService.findUsersByCourseId(course.getId()));

        return "course";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("addNewCourse")
    public String addNewCourse(Model model,
                               @RequestParam String courseTitle,
                               @RequestParam(required = false) String courseDescription,
                               @RequestParam String startDate,
                               @RequestParam String endDate,
                               @RequestParam(required = false) Integer studentsLimit,
                               @RequestParam(required = false) MultipartFile courseImage) {

        LocalDate newStartDate = utilService.stringToLocalDate(startDate);
        LocalDate newEndDate = utilService.stringToLocalDate(endDate);
        String image = fileService.saveFile(courseImage);

        Course course = courseService.addNewCourse(courseTitle, courseDescription, newStartDate,
                                                              newEndDate, studentsLimit, image);

        boolean isSaveOk = courseService.save(course);
        if (!isSaveOk) model.addAttribute("courseTitleError", "This course already exists");

        model.addAttribute("courses", courseService.findActuallyCourses());

        return "courseList";
    }

    @Transactional
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("deleteCourse")
    public String deleteCourse(@RequestParam("courseId") Course course) {

        scheduleService.deleteAllScheduleForCourse(course.getId());
        courseService.deleteCourse(course);

        return "redirect:/courseList";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("{course}/changeSchedule")
    public String changeSchedule(@PathVariable Course course,
                                 @RequestParam User user,
                                 @RequestParam String date,
                                 @RequestParam(required = false) PresenceStatus presenceStatus,
                                 @RequestParam(required = false) Score score) {

        LocalDate scheduleDate = utilService.stringToLocalDate(date);

        Schedule schedule = scheduleService
                .getScheduleByDateUserAndCourseId(scheduleDate, user.getId(), course.getId());
        schedule.setPresenceStatus(presenceStatus);
        schedule.setScore(score);

        scheduleService.save(schedule);

        return "redirect:/courseList/{course}";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{course}/deleteUser")
    public String deleteUserFromCourse(@PathVariable Course course,
                                       @RequestParam String userId) {

        userService.getUserListFromStringWithIds(userId)
                .forEach(user -> {
                    userService.deleteUserFromCourse(course, user);
                    scheduleService.deleteCourseScheduleForUser(course.getId(), user.getId());
                });

        return "redirect:/courseList/{course}";
    }
}
