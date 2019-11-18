package com.instahipsta.webappTest.service;

import com.instahipsta.webappTest.domain.*;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.ScheduleServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class ScheduleServiceTest {

    private Schedule schedule;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private CourseServiceImpl courseService;

    @Before
    public void createSchedule() {
        schedule = new Schedule();
        schedule.setId(12L);
        schedule.setScore(Score.NONE);
        schedule.setPresenceStatus(PresenceStatus.NONE);
        schedule.setCourse(courseService.findCourseById(1L));
        schedule.setUser(userService.findUserById(1L));
        schedule.setDate(LocalDate.now());
    }

    @Test
    @Sql(value = {"/create-courses-before-schedule-service.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getScheduleByUserAndCourseId() throws Exception {
        Set<Schedule> schedules = scheduleService.getScheduleByUserAndCourseId(1L, 1L);
        Assert.assertEquals(5, schedules.size());
    }

    @Test
    @Sql(value = {"/create-courses-before-schedule-service.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getScheduleDaysByCourseId() throws Exception {
        List<Date> scheduleDates = scheduleService.getScheduleDaysByCourseId(3L);
        Assert.assertEquals(6, scheduleDates.size());
    }

    @Test
    @Sql(value = {"/create-courses-before-schedule-service.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteCourseScheduleForUser() throws Exception {
        scheduleService.deleteCourseScheduleForUser(1L, 1L);
        Assert.assertTrue(scheduleService.getScheduleByUserAndCourseId(1L, 1L).isEmpty());
    }

    @Test
    @Sql(value = {"/create-courses-before-schedule-service.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getScheduleByDateUserAndCourseId() throws Exception {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse("2019-11-16", format);
        Schedule schedule = scheduleService.getScheduleByDateUserAndCourseId(date, 1L, 1L);
        Assert.assertNotNull(schedule);
    }
    @Test
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void save() throws Exception {
        Assert.assertTrue(scheduleService.save(this.schedule));
    }

    @Test
    @Sql(value = {"/create-courses-before-schedule-service.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAllScheduleForCourse() throws Exception {
        scheduleService.deleteAllScheduleForCourse(1L);
        Assert.assertTrue(scheduleService.getScheduleDaysByCourseId(1L).isEmpty());
    }

    @Test
    @Sql(value = {"/create-courses-before-schedule-service.sql", "/create-user-before-schedule-service.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void scheduleFactory() throws Exception {
        User user = userService.findUserById(2L);
        Course course = courseService.findCourseById(1L);
        scheduleService.scheduleFactory(user, course);
        Assert.assertEquals(5, scheduleService.getScheduleByUserAndCourseId(2L, 1L).size());
    }
}
