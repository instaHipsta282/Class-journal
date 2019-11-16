package com.instahipsta.webappTest.repos;

import com.instahipsta.webappTest.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {

    @Query(value = "SELECT * " +
            "FROM schedule " +
            "WHERE user_id = ?1 " +
            "AND course_id = ?2 " +
            "ORDER BY date"
            , nativeQuery = true)
    Set<Schedule> getScheduleByUserAndCourseId(Long userId, Long courseId);

    @Query(value = "SELECT DISTINCT(date) " +
            "FROM schedule " +
            "WHERE course_id = ?1 "
            , nativeQuery = true)
    List<Date> getScheduleDaysByCourseId(Long courseId);

    @Query(value = "SELECT * " +
            "FROM schedule " +
            "WHERE course_id = ?3 " +
            "AND user_id = ?2 " +
            "AND date = ?1"
            , nativeQuery = true)
    Schedule getScheduleByDateUserAndCourseId(LocalDate date, Long userId, Long courseId);

    void deleteAllByCourseId(Long courseId);

    void deleteByCourseIdAndUserId(long courseId, long userId);
}

