package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
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

    void deleteCourse(Course course);

    Boolean doesThisCourseExist(String courseTitle, LocalDate startDate, LocalDate endDate);

    Date getStartDateById(Long courseId);

    Date getEndDateById(Long courseId);

    Set<Course> findPresentCourses();

    Set<Course> findAvailableCoursesForUser(User user);

    List<LocalDate> getScheduleDays(LocalDate startDate, LocalDate endDate);

    List<User> getNewUsersForCourse(Course course);

    Course addNewCourse(String courseTitle,
                        String courseDescription,
                        LocalDate startDate,
                        LocalDate endDate,
                        Integer studentsLimit,
                        String  image);

    Model addDataToModel(User user, Model model);

    Integer addCourseFromForm(User user, Set<Course> courses, Map<String, String> form);
}
