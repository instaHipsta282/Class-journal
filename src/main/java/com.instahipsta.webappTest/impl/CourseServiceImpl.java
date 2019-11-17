package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.repos.CourseRepo;
import com.instahipsta.webappTest.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.LimitExceededException;
import java.time.LocalDate;
import java.util.*;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    //testing
    @Override
    public boolean save(Course course) { return courseRepo.save(course) != null; }

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
}
