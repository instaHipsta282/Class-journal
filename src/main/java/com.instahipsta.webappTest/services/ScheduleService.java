package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.Schedule;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface ScheduleService {

    void addSchedule(Schedule schedule);

    void deleteCourseScheduleForUser(long courseId, long userId);

    Schedule getScheduleByDateUserAndCourseId(LocalDate date, Long userId, Long courseId);

    Set<Schedule> getScheduleByUserAndCourseId(Long userId, Long courseId);

    List<Date> getScheduleDaysByCourseId(Long courseId);

    void save(Schedule schedule);

    void deleteAllScheduleForCourse(long courseId);
}
