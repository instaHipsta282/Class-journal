package com.instahipsta.webappTest.repos;

import com.instahipsta.webappTest.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public interface CourseRepo extends JpaRepository<Course, Long> {

    @Query(value = "SELECT * " +
                   "FROM course c " +
                   "WHERE c.end_date > ?1", nativeQuery = true)
    Set<Course> findActuallyCourses(Date end_date);

    @Query(value = "SELECT * " +
            "FROM course c " +
            "WHERE c.end_date < ?1", nativeQuery = true)
    Set<Course> findPassCourses(Date currentDate);

    @Query(value = "SELECT * " +
            "FROM course c " +
            "WHERE c.start_date > ?1", nativeQuery = true)
    Set<Course> findFutureCourses(Date currentDate);

    @Query(value = "SELECT * " +
            "FROM course c " +
            "WHERE c.start_date <= ?1 " +
            "AND c.end_date > ?1", nativeQuery = true)
    Set<Course> findPresentCourses(Date currentDate);

    @Query(value = "SELECT * " +
                   "FROM course c " +
                   "WHERE c.id IN (" +
                   "SELECT course_id " +
                   "FROM course_usr " +
                   "WHERE user_id = ?1) " +
                   "AND end_date > ?2", nativeQuery = true)
    Set<Course> findActuallyCoursesByUserId(long userId, Date currentDate);

    @Query(value = "SELECT count(*) " +
            "FROM course c " +
            "WHERE c.title = ?1 " +
            "AND c.start_date = ?2 " +
            "AND c.end_date = ?3"
            , nativeQuery = true)
    Integer findCourseByTitleAndDates(String courseTitle, LocalDate startDate, LocalDate endDate);

    @Query(value = "SELECT start_date " +
            "FROM course c " +
            "WHERE c.id = ?1"
            , nativeQuery = true)
    Date getStartDateById(Long courseId);

    @Query(value = "SELECT end_date " +
            "FROM course c " +
            "WHERE c.id = ?1"
            , nativeQuery = true)
    Date getEndDateById(Long courseId);

    @Query(value = "SELECT * " +
            "FROM course c " +
            "WHERE c.id = ?1"
            , nativeQuery = true)
    Course findCourseById(long courseId);

}
