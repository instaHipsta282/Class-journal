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

    @Override
    public boolean save(Course course) {
        return courseRepo.save(course) != null;
    }

    @Override
    public Set<Course> findActuallyCoursesByUserId(long userId) {
        return new HashSet<>(courseRepo.findActuallyCoursesByUserId(userId));
    }

    @Override
    public Set<Course> findAll() {
        return new HashSet<>(courseRepo.findAll());
    }

    @Override
    public Set<Course> findActuallyCourses() {
        Date currentDate = new Date();

        return new HashSet<>(courseRepo.findActuallyCourses(currentDate));
    }

    @Override
    public Set<Course> findPresentCourses() {
        Date currentDate = new Date();

        return new HashSet<>(courseRepo.findPresentCourses(currentDate));
    }

    @Override
    public Set<Course> findPassCourses() {
        Date currentDate = new Date();

        return new HashSet<>(courseRepo.findPassCourses(currentDate));
    }

    @Override
    public Set<Course> findFutureCourses() {
        Date currentDate = new Date();

        return new HashSet<>(courseRepo.findFutureCourses(currentDate));
    }

    @Override
    public Map<Course, String> actuallyCoursesWithPercent() {
        Map<Course, String> map = new HashMap<>();
        Set<Course> courses = findActuallyCourses();
        courses.forEach(c -> map.put(c, percentCounter(c.getStudentsLimit(), c.getStudentsCount())));
        return map;
    }

    @Override
    public Course findCourseById(long id) {
        return courseRepo.getOne(id);
    }

    @Override
    public void addCourse(Course course) {
        courseRepo.save(course);
    }

    @Override
    public void deleteCourse(Course course) {
        courseRepo.delete(course);
    }

    @Override
    public Boolean doesThisCourseExist(String courseTitle, LocalDate startDate, LocalDate endDate) {
        int count = courseRepo.findCourseByTitleAndDates(courseTitle, startDate, endDate);
        return count == 1;
    }

    @Override
    public Date getStartDateById(Long courseId) {
        return courseRepo.getStartDateById(courseId);
    }

    @Override
    public Date getEndDateById(Long courseId) {
        return courseRepo.getEndDateById(courseId);
    }

    private String percentCounter(int fullNumber, int partOfNumber) {
        double onePercent = ((double) fullNumber / 100);
        double percent = partOfNumber / onePercent;
        return String.format("%.1f", percent).replace(',', '.');
    }

    @Transactional
    public void addUserToCourse(Course course, User user) {
        if (course.getStudentsLimit() > course.getStudentsCount()) {
            course.getStudents().add(user);
            course.setStudentsCount(course.getStudentsCount() + 1);
            save(course);
            scheduleService.scheduleFactory(user, course);
        } else {
            try {
                throw new LimitExceededException("Course is full");
            } catch (LimitExceededException e) {
                e.getMessage();
            }
        }
    }
}
