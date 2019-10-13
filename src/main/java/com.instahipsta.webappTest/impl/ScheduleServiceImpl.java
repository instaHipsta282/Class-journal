package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.domain.Schedule;
import com.instahipsta.webappTest.repos.ScheduleRepo;
import com.instahipsta.webappTest.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepo scheduleRepo;

    @Override
    public void addSchedule(Schedule schedule) {
        scheduleRepo.save(schedule);
    }

}
