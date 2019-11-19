package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.repos.CourseRepo;
import com.instahipsta.webappTest.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import javax.naming.LimitExceededException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UtilServiceImpl utilService;

    //testing
    @Override
    public boolean save(Course course) {
        if (!doesThisCourseExist(course.getTitle(), course.getStartDate(), course.getEndDate())) {
            courseRepo.save(course);
            return true;
        }
        return false;
    }

    //testing
    @Override
    public Set<Course> findActuallyCoursesByUserId(long userId) {
        return new HashSet<>(courseRepo.findActuallyCoursesByUserId(userId, new Date()));
    }

    //testing
    @Override
    public Set<Course> findAll() {
        return new HashSet<>(courseRepo.findAll());
    }

    //testing
    @Override
    public Set<Course> findActuallyCourses() {
        Date currentDate = new Date();

        return new HashSet<>(courseRepo.findActuallyCourses(currentDate));
    }

    //testing
    @Override
    public Set<Course> findPresentCourses() {
        Date currentDate = new Date();

        return new HashSet<>(courseRepo.findPresentCourses(currentDate));
    }

    //testing
    @Override
    public Set<Course> findPassCourses() {
        Date currentDate = new Date();

        return new HashSet<>(courseRepo.findPassCourses(currentDate));
    }

    //testing
    @Override
    public Set<Course> findFutureCourses() {
        Date currentDate = new Date();

        return new HashSet<>(courseRepo.findFutureCourses(currentDate));
    }

    //testing
    @Override
    public Map<Course, String> actuallyCoursesWithPercent() {
        Map<Course, String> map = new HashMap<>();
        Set<Course> courses = findActuallyCourses();
        courses.forEach(c -> map.put(c, percentCounter(c.getStudentsLimit(), c.getStudentsCount())));
        return map;
    }

    //testing
    @Override
    public Course findCourseById(long id) {
        Course course = courseRepo.findCourseById(id);
        return courseRepo.findCourseById(id);
    }

    //testing
    @Override
    public void deleteCourse(Course course) {
        courseRepo.delete(course);
    }

    //testing
    @Override
    public Boolean doesThisCourseExist(String courseTitle, LocalDate startDate, LocalDate endDate) {
        int count = courseRepo.findCourseByTitleAndDates(courseTitle, startDate, endDate);
        return count == 1;
    }

    //testing
    @Override
    public Date getStartDateById(Long courseId) {
        return courseRepo.getStartDateById(courseId);
    }

    //testing
    @Override
    public Date getEndDateById(Long courseId) {
        return courseRepo.getEndDateById(courseId);
    }

    //testing
    public String percentCounter(int fullNumber, int partOfNumber) {
        double onePercent = ((double) fullNumber / 100);
        double percent = partOfNumber / onePercent;
        return String.format("%.1f", percent).replace(',', '.');
    }

    //testing
    @Transactional
    public boolean addUserToCourse(Course course, User user) {
        if (course.getStudentsLimit() > course.getStudentsCount()) {
            course.getStudents().add(user);
            course.setStudentsCount(course.getStudentsCount() + 1);
            save(course);
            scheduleService.scheduleFactory(user, course);
            return true;
        } else {
            try {
                throw new LimitExceededException("Course is full");
            } catch (LimitExceededException e) {
                e.getMessage();
                return false;
            }
        }
    }

    //testing
    @Override
    public Set<Course> findAvailableCoursesForUser(User user) {
        Set<Course> availableCourses = findActuallyCourses();
        availableCourses.removeAll(findActuallyCoursesByUserId(user.getId()));

        return availableCourses;
    }

    //testing
    @Override
    public List<LocalDate> getScheduleDays(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> scheduleDays = new ArrayList<>();
        while (startDate.isBefore(endDate.plusDays(1))) {
            scheduleDays.add(startDate);
            startDate = startDate.plusDays(1);
        }
        return scheduleDays;
    }

    //testing
    @Override
    public List<User> getNewUsersForCourse(Course course) {
        List<User> usersForCourse = userService
                .findAll()
                .stream()
                .filter(u -> !course.getStudents().contains(u))
                .collect(Collectors.toList());
        return usersForCourse;
    }

    //testing
    @Override
    public Course addNewCourse(String courseTitle,
                               String courseDescription,
                               LocalDate startDate,
                               LocalDate endDate,
                               Integer studentsLimit,
                               String image) {

        Course course = new Course();
        course.setTitle(courseTitle);
        course.setStartDate(startDate);
        course.setEndDate(endDate);
        course.setStudentsCount(0);
        course.setDaysCount((int) ChronoUnit.DAYS.between(startDate, endDate) + 1);
        if (studentsLimit != null) course.setStudentsLimit(studentsLimit);
        if (courseDescription != null) course.setDescription(courseDescription);
        if (image != null) course.setImage(image);

        return course;
    }

    //testing
    @Override
    public Model addDataToModel(User user, Model model) {

        Set<Course> courses = findActuallyCourses();
        courses.removeAll(findActuallyCoursesByUserId(user.getId()));

        model.addAttribute("currentUser", user);
        model.addAttribute("userCourses", findActuallyCoursesByUserId(user.getId()));
        model.addAttribute("courses", courses);

        return model;
    }

    //testing
    @Override
    public Integer addCourseFromForm(User user, Set<Course> courses, Map<String, String> form) {
        int[] count = {0};
        courses.stream()
                .filter(course -> form.containsKey(course.getId().toString()))
                .forEach(course -> {
                    boolean add = addUserToCourse(course, user);
                    if (add) count[0] += 1;
                });
        return count[0];
    }
}
