package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.Course;

import java.util.Set;

public interface CourseService {

    Set<Course> findAll();

    Set<Course> findAvailableCourses();

    Set<Course> findActuallyCoursesByUserId(long userId);

    Course findCoursesById(long id);

    void addCourse(Course course);

    void deleteCourse(Course course);
}
