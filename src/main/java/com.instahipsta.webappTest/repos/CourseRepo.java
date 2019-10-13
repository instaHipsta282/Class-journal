package com.instahipsta.webappTest.repos;

import com.instahipsta.webappTest.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.Set;

public interface CourseRepo extends JpaRepository<Course, Long> {

    @Query(value = "SELECT * " +
                   "FROM course c " +
                   "WHERE c.end_date > ?1", nativeQuery = true)
    Set<Course> findActuallyCourses(Date end_date);

    @Query(value = "SELECT * " +
                   "FROM course c " +
                   "WHERE c.id IN (" +
                   "SELECT course_id " +
                   "FROM course_usr " +
                   "WHERE user_id = ?1" +
                   ")", nativeQuery = true)
    Set<Course> findActuallyCoursesByUserId(long userId);
}
