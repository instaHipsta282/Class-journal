package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.repos.CourseRepo;
import com.instahipsta.webappTest.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseRepo courseRepo;

    @Override
    public Set<Course> findActuallyCoursesByUserId(long userId) {
        return new HashSet<>(courseRepo.findActuallyCoursesByUserId(userId));
    }

    @Override
    public Set<Course> findAll() {
        return new HashSet<>(courseRepo.findAll());
    }

    @Override
    public Set<Course> findAvailableCourses() {
        Date currentDate = new Date();

        return new HashSet<>(courseRepo.findActuallyCourses(currentDate));
    }

    @Override
    public Course findCoursesById(long id) {

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

}
