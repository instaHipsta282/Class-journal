package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.Course;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public interface CourseService {

    Set<Course> findAll();

    Set<Course> findAvailableCourses();

    Set<Course> findActuallyCoursesByUserId(long userId);

    Course findCoursesById(long id);

    void addCourse(Course course);

    void deleteCourse(Course course);

    Boolean doesThisCourseExist(String courseTitle, LocalDate startDate, LocalDate endDate);


    Date getStartDateById(Long courseId);

    Date getEndDateById(Long courseId);

}
