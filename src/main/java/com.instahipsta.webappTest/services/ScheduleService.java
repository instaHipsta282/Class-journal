package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.Schedule;
import com.instahipsta.webappTest.domain.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ScheduleService {

    void deleteCourseScheduleForUser(long courseId, long userId);

    Schedule getScheduleByDateUserAndCourseId(LocalDate date, Long userId, Long courseId);

    Set<Schedule> getScheduleByUserAndCourseId(Long userId, Long courseId);

    List<Date> getScheduleDaysByCourseId(Long courseId);

    boolean save(Schedule schedule);

    void deleteAllScheduleForCourse(long courseId);

    //testing
    void scheduleFactory(User student, Course course);
}
