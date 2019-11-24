package com.instahipsta.webappTest.service;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import com.instahipsta.webappTest.impl.UtilServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class CourseServiceTest {
    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private UtilServiceImpl utilService;

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
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findActuallyCoursesByUserId() throws Exception {
        Set<Course> courses = courseService.findActuallyCoursesByUserId(1);
        courses.forEach(s -> System.out.println(s.getTitle()));
        Assert.assertEquals(2, courses.size());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAll() throws Exception {
        Assert.assertEquals(3, courseService.findAll().size());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findActuallyCourses() throws Exception {
        Set<Course> courses = courseService.findActuallyCourses();
        Assert.assertEquals(2, courses.size());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findPresentCourses() throws Exception {
        Course course = courseService.findPresentCourses().iterator().next();
        Assert.assertEquals("Present", course.getTitle());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findPassCourses() throws Exception {
        Course course = courseService.findPassCourses().iterator().next();
        Assert.assertEquals("NotActually", course.getTitle());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findFutureCourses() throws Exception {
        Course course = courseService.findFutureCourses().iterator().next();
        Assert.assertEquals("Future", course.getTitle());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void actuallyCoursesWithPercent() throws Exception {
        Map<Course, String> courses = courseService.actuallyCoursesWithPercent();
        Assert.assertEquals(2, courses.size());
        Assert.assertEquals("20.0", courses.values().iterator().next());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findCourseById() throws Exception {
        Assert.assertNotNull(courseService.findCourseById(1));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteCourse() throws Exception {
        Course course = courseService.findCourseById(1);
        courseService.deleteCourse(course);
        Assert.assertNull(courseService.findCourseById(1));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void doesThisCourseExist() throws Exception {
        String title = "NotActually";
        LocalDate startDate =  utilService.stringToLocalDate("2019-09-03");
        LocalDate endDate = utilService.stringToLocalDate("2019-09-15");
        Assert.assertTrue(courseService.doesThisCourseExist(title, startDate, endDate));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getStartDateById() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = format.parse("2019-11-19");
        Assert.assertEquals(startDate, courseService.getStartDateById(1L));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getEndDateById() throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date endDate = format.parse("2019-09-15");
        Assert.assertEquals(endDate, courseService.getEndDateById(2L));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUserToCourse() throws Exception {
        Course course = courseService.findCourseById(1);
        User user = userService.findUserById(1);
        Assert.assertTrue(courseService.addUserToCourse(course, user));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findAvailableCoursesForUser() throws Exception {
        User user = userService.findUserById(2);
        Set<Course> courses = courseService.findAvailableCoursesForUser(user);
        Assert.assertEquals(2, courses.size());
    }

    @Test
    @Sql(value = {"/create-course-before.sql", "/create-user-before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getScheduleDays() throws Exception {
        Course course = courseService.findCourseById(1L);
        List<LocalDate> scheduleDays = courseService.getScheduleDays(course.getStartDate(), course.getEndDate());
        Assert.assertEquals(18, scheduleDays.size());
    }

    @Test
    @Sql(value = {"/create-course-before.sql", "/create-user-before.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getNewUsersForCourseTest() throws Exception {
        Course course = courseService.findCourseById(1L);
        List<User> users = courseService.getNewUsersForCourse(course);
        Assert.assertEquals(3, users.size());
    }

    @Test
    public void addNewCourseDescriptionNullTest() throws Exception {
        LocalDate startDate = utilService.stringToLocalDate("2019-03-11");
        LocalDate endDate = utilService.stringToLocalDate("2019-04-11");

        Course course = courseService.addNewCourse("Data Science",null,
                                    startDate, endDate, 5, null);
        Assert.assertEquals("Data Science", course.getTitle());
        Assert.assertEquals(startDate, course.getStartDate());
        Assert.assertEquals(endDate, course.getEndDate());
        Assert.assertEquals(5, course.getStudentsLimit());
        Assert.assertEquals(0, course.getStudentsCount());
        Assert.assertEquals(32, course.getDaysCount());
        Assert.assertEquals(32, course.getDaysCount());
        Assert.assertEquals("Description for this course has not yet been added.", course.getDescription());
        Assert.assertEquals("courseDefaultImage.jpg", course.getImage());
    }

    @Test
    public void addNewCourseDescriptionExistTest() throws Exception {
        LocalDate startDate = utilService.stringToLocalDate("2019-03-11");
        LocalDate endDate = utilService.stringToLocalDate("2019-04-11");

        Course course = courseService.addNewCourse("Data Science","Something description",
                startDate, endDate, 5, null);
        Assert.assertEquals("Data Science", course.getTitle());
        Assert.assertEquals(startDate, course.getStartDate());
        Assert.assertEquals(endDate, course.getEndDate());
        Assert.assertEquals(5, course.getStudentsLimit());
        Assert.assertEquals(0, course.getStudentsCount());
        Assert.assertEquals(32, course.getDaysCount());
        Assert.assertEquals(32, course.getDaysCount());
        Assert.assertEquals("Something description", course.getDescription());
        Assert.assertEquals("courseDefaultImage.jpg", course.getImage());
    }

    @Test
    public void addDataToModel() {
        User user = userService.findUserById(1L);
        Model model = new ExtendedModelMap();
        Model resultModel = courseService.addDataToModel(user, model);
        Assert.assertTrue(resultModel.containsAttribute("currentUser"));
        Assert.assertTrue(resultModel.containsAttribute("userCourses"));
        Assert.assertTrue(resultModel.containsAttribute("courses"));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addCourseFromForm() {
        User user = userService.findUserById(2L);
        Set<Course> availableCourses = courseService.findAvailableCoursesForUser(user);

        Map<String, String> courses = new HashMap<>();
        courses.put("1", "firstCourse");
        courses.put("3", "secondCourse");

        int count = courseService.addCourseFromForm(user, availableCourses, courses);
        Assert.assertEquals(2, count);
    }
}
