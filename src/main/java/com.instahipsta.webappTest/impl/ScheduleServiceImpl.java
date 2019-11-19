package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.Schedule;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.repos.ScheduleRepo;
import com.instahipsta.webappTest.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepo;

    //testing
    @Override
    public Set<Schedule> getScheduleByUserAndCourseId(Long userId, Long courseId) {
        return scheduleRepo.getScheduleByUserAndCourseId(userId, courseId);
    }

    //testing
    @Override
    public List<Date> getScheduleDaysByCourseId(Long courseId) {
        return scheduleRepo.getScheduleDaysByCourseId(courseId);
    }

    //testing
    @Transactional
    @Override
    public void deleteCourseScheduleForUser(long courseId, long userId) {
        scheduleRepo.deleteByCourseIdAndUserId(courseId, userId);
    }

    //testing
    @Override
    public Schedule getScheduleByDateUserAndCourseId(LocalDate date, Long userId, Long courseId) {
        return scheduleRepo.getScheduleByDateUserAndCourseId(date, userId, courseId);
    }

    //testing
    @Override
    public boolean save(Schedule schedule) {
        return scheduleRepo.save(schedule) != null;
    }

    //testing
    @Transactional
    @Override
    public void deleteAllScheduleForCourse(long courseId) { scheduleRepo.deleteAllByCourseId(courseId); }

    //testing
    @Override
    public void scheduleFactory(User student, Course course) {
        for (int i = 0; i < course.getDaysCount(); i++) {
            Schedule schedule = new Schedule();

            schedule.setDate(course.getStartDate().plusDays(i));
            schedule.setUser(student);
            schedule.setCourse(course);
            save(schedule);
        }
    }

    //testing
    @Override
    @Transactional
    public void deleteAllScheduleForUser(User user) {
        if (user != null) scheduleRepo.deleteAllByUserId(user.getId());
    }

    //testing
    @Override
    public Set<Schedule> getScheduleByUser(User user) {
        return scheduleRepo.findAllByUserId(user.getId());
    }
}
