package com.instahipsta.webappTest.Service;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
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

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class CourseServiceTest {
    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private UserServiceImpl userService;

    private Course course;

    @Before
    public void createCourse() {
        course = new Course();
        course.setStudentsLimit(5);
        course.setDaysCount(5);
        course.setTitle("Jojo");
        course.setEndDate(LocalDate.now().plusDays(1));
        course.setStartDate(LocalDate.now());
        course.setId(10L);
    }

    @Test
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void save() throws Exception {
        Assert.assertTrue(courseService.save(course));
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findActuallyCoursesByUserId() throws Exception {
        Set<Course> courses = courseService.findActuallyCoursesByUserId(1);
        courses.forEach(s -> System.out.println(s.getTitle()));
        Assert.assertEquals(1, courses.size());
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAll() throws Exception {
        Assert.assertEquals(3, courseService.findAll().size());
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findActuallyCourses() throws Exception {
        Set<Course> courses = courseService.findActuallyCourses();
        Assert.assertEquals(2, courses.size());
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findPresentCourses() throws Exception {
        Course course = courseService.findPresentCourses().iterator().next();
        Assert.assertEquals("Present", course.getTitle());
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findPassCourses() throws Exception {
        Course course = courseService.findPassCourses().iterator().next();
        Assert.assertEquals("NotActually", course.getTitle());
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findFutureCourses() throws Exception {
        Course course = courseService.findFutureCourses().iterator().next();
        Assert.assertEquals("Future", course.getTitle());
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void actuallyCoursesWithPercent() throws Exception {
        Map<Course, String> courses = courseService.actuallyCoursesWithPercent();
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals("20.0", courses.values().iterator().next());
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findCourseById() throws Exception {
        Assert.assertNotNull(courseService.findCourseById(1));
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteCourse() throws Exception {
        Course course = courseService.findCourseById(1);
        courseService.deleteCourse(course);
        Assert.assertNull(courseService.findCourseById(1));
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void doesThisCourseExist() throws Exception {
        String title = "NotActually";
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy.MM.dd");
        LocalDate startDate = LocalDate.parse("2019.09.03", format);
        LocalDate endDate = LocalDate.parse("2019.09.15", format);
        Assert.assertTrue(courseService.doesThisCourseExist(title, startDate, endDate));
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getStartDateById() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("2019-11-16");
        Assert.assertEquals(startDate, courseService.getStartDateById(1L));
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getEndDateById() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = format.parse("2019-09-15");
        Assert.assertEquals(endDate, courseService.getEndDateById(2L));
    }
    @Test
    public void percentCounter() {
        Assert.assertEquals("93.3", courseService.percentCounter(60, 56));
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUserToCourse() throws Exception {
        Course course = courseService.findCourseById(1);
        User user = userService.findUserById(1);
        Assert.assertTrue(courseService.addUserToCourse(course, user));
    }

    @Test
    @Sql(value = {"/create-courses-before-course-controller.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAvailableCoursesForUser() throws Exception {
        User user = userService.findUserById(1);
        Set<Course> courses = courseService.findAvailableCoursesForUser(user);
        Assert.assertEquals(1, courses.size());
    }


}
