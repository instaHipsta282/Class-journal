package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.domain.Schedule;
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
    public void addSchedule(Schedule schedule) {
        scheduleRepo.save(schedule);
    }

    @Override
    public Set<Schedule> getScheduleByUserAndCourseId(Long userId, Long courseId) {
        return scheduleRepo.getScheduleByUserAndCourseId(userId, courseId);
    }

    @Override
    public List<Date> getScheduleDaysByCourseId(Long courseId) {
        return scheduleRepo.getScheduleDaysByCourseId(courseId);
    }

    @Override
    public Schedule getScheduleByDateUserAndCourseId(LocalDate date, Long userId, Long courseId) {
        return scheduleRepo.getScheduleByDateUserAndCourseId(date, userId, courseId);
    }

    @Override
    public void save(Schedule schedule) {
        scheduleRepo.save(schedule);
    }

}
