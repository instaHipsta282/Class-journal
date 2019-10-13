package com.instahipsta.webappTest.repos;

import com.instahipsta.webappTest.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepo extends JpaRepository<Schedule, Long> {

}
