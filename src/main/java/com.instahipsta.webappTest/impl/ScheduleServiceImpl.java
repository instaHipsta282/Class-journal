package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.Schedule;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.repos.ScheduleRepo;
import com.instahipsta.webappTest.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Override
    public Set<Schedule> getScheduleByUserAndCourseId(Long userId, Long courseId) {
        return scheduleRepo.getScheduleByUserAndCourseId(userId, courseId);
    }

    @Override
    public List<Date> getScheduleDaysByCourseId(Long courseId) {
        return scheduleRepo.getScheduleDaysByCourseId(courseId);
    }

    @Override
    public void deleteCourseScheduleForUser(long courseId, long userId) {
        scheduleRepo.deleteByCourseIdAndUserId(courseId, userId);
    }

    @Override
    public Schedule getScheduleByDateUserAndCourseId(LocalDate date, Long userId, Long courseId) {
        return scheduleRepo.getScheduleByDateUserAndCourseId(date, userId, courseId);
    }

    @Override
    public void save(Schedule schedule) {
        scheduleRepo.save(schedule);
    }

    @Override
    public void deleteAllScheduleForCourse(long courseId) { scheduleRepo.deleteAllByCourseId(courseId); }

    public void scheduleFactory(User student, Course course) {
        for (int i = 0; i < course.getDaysCount(); i++) {
            Schedule schedule = new Schedule();

            schedule.setDate(course.getStartDate().plusDays(i));
            schedule.setUser(student);
            schedule.setCourse(course);
            save(schedule);
        }
    }

}
