package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.Course;

import java.time.LocalDate;
import java.util.Date;
import java.util.Map;
import java.util.Set;

public interface CourseService {

    Course findCourseById(long courseId);

    Set<Course> findAll();

    Set<Course> findActuallyCourses();

    Set<Course> findPassCourses();

    Set<Course> findFutureCourses();

    boolean save(Course course);

    Set<Course> findActuallyCoursesByUserId(long userId);

    Map<Course, String> actuallyCoursesWithPercent();

    void addCourse(Course course);

    void deleteCourse(Course course);

    Boolean doesThisCourseExist(String courseTitle, LocalDate startDate, LocalDate endDate);

    Date getStartDateById(Long courseId);

    Date getEndDateById(Long courseId);

    Set<Course> findPresentCourses();

}
