package com.instahipsta.webappTest.service;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.Role;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;
    private User user;
    @Autowired
    private CourseServiceImpl courseService;

    @Before
    public void createUser() {
        user = new User();
        user.setActive(true);
        user.setEmail("asas@test.com");
        user.setPassword("1");
        user.setActivationCode("123456");
        user.setUsername("Test");
        user.setLastName("Test");
        user.setFirstName("Test");
        user.setPhone("89999999999");
    }

    @Test
    public void findUserById() throws Exception {
        User user = userService.findUserById(1);
        Assert.assertNotNull(user);
    }

    @Test
    public void findUserByIdDoesntFind() throws Exception {
        User user = userService.findUserById(10011);
        Assert.assertNull(user);
    }

    @Test
    public void findAll() throws Exception {
        List<User> users = userService.findAll();
        Assert.assertEquals(1, users.size());
    }

    @Test
    public void findByLastNameAndFirstName() throws Exception {
        User user = userService.findByLastNameAndFirstName("Anderson", "Poll").get(0);
        Assert.assertNotNull(user);
    }

    @Test
    public void findByLastNameAndFirstNameDoesntFind() throws Exception {
        int count = userService.findByLastNameAndFirstName("Reno", "Logan").size();
        Assert.assertEquals(0, count);
    }

    @Test
    public void isEmailAlreadyUse() throws Exception {
        boolean exist = userService.isEmailAlreadyUse("stepaden@mail.ru");
        Assert.assertTrue(exist);
    }

    @Test
    public void isEmailAlreadyUseDoesntFind() throws Exception {
        boolean notExist = userService.isEmailAlreadyUse("test@mail.ru");
        Assert.assertFalse(notExist);
    }

    @Test
    public void getUserListFromStringWithIds() throws Exception {
        User user = userService.getUserListFromStringWithIds("1").get(0);
        Assert.assertNotNull(user);
    }

    @Test
    public void getUserListFromStringWithIdsDoesntFind() throws Exception {
        User user = userService.getUserListFromStringWithIds("19213").get(0);
        Assert.assertNull(user);
    }

    @Test(expected = NumberFormatException.class)
    public void getUserListFromStringWithIdsWrongArg() throws Exception {
        userService.getUserListFromStringWithIds("wefwf").get(0);
    }

    @Test
    public void getUserListFromStringWithName() throws Exception {
        List<User> users = userService.getUserListFromStringWithName("Anderson Poll");
        Assert.assertNotNull(users.get(0));
    }

    @Test(expected = WrongNumberArgsException.class)
    public void getUserListFromStringWithNameOneArg() throws Exception {
        userService.getUserListFromStringWithName("Rafwf");
    }

    @Test(expected = WrongNumberArgsException.class)
    public void getUserListFromStringWithNameThreeArg() throws Exception {
        userService.getUserListFromStringWithName("Rafwf wefewf wfwef");
    }

    @Test
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void save() throws Exception {
        User currentUser = userService.save(user);
        Assert.assertNotNull(currentUser);
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changePassword() throws Exception {
        User user = userService.findUserById(2);
        String oldPassword = user.getPassword();
        userService.changePassword(user, "2");
        Assert.assertNotEquals(oldPassword, user.getPassword());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changePhone() throws Exception {
        User user = userService.findUserById(2);
        userService.changePhone(user, "000000000");
        Assert.assertEquals("000000000", user.getPhone());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeFirstName() throws Exception {
        User user = userService.findUserById(2);
        userService.changeFirstName(user,"Kesha");
        Assert.assertEquals("Kesha", user.getFirstName());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeSecondName() throws Exception {
        User user = userService.findUserById(2);
        userService.changeSecondName(user,"Kesha");
        Assert.assertEquals("Kesha", user.getSecondName());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeLastName() throws Exception {
        User user = userService.findUserById(2);
        userService.changeLastName(user,"Kesha");
        Assert.assertEquals("Kesha", user.getLastName());
    }

    @Test
    public void loadUserByUsername() throws Exception {
        UserDetails userDetails = userService.loadUserByUsername("ADMIN");
        Assert.assertEquals("ADMIN", userDetails.getUsername());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void activateUser() throws Exception {
        User user = userService.findUserById(2);
        boolean isActivate = userService.activateUser(user.getActivationCode());
        Assert.assertTrue(isActivate);
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void sendMessageWithActivationCode() throws Exception {
        User user = userService.findUserById(2);
        String message = userService.sendMessageWithActivationCode(user);
        Assert.assertThat(message, containsString(user.getUsername()));
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeEmail() throws Exception {
        User user = userService.findUserById(2);
        String email = "test@test.test";
        userService.changeEmail(user, email);
        Assert.assertEquals(email, user.getEmail());
        Assert.assertNotNull(user.getActivationCode());
    }

    @Test
    @Sql(value = {"/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUser() throws Exception {
        userService.addUser(user);
        Assert.assertTrue(user.isActive());
        Assert.assertTrue(user.getRoles().contains(Role.USER));
        Assert.assertNotEquals("123456", user.getActivationCode());
        Assert.assertNotEquals("123", user.getPassword());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void findUsersByCourseId() throws Exception {
        List<User> users = userService.findUsersByCourseId(1);
        Assert.assertEquals(1, users.size());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteUserFromCourse() throws Exception {
        Course course = courseService.findCourseById(1);
        User user = userService.findUserById(1);
        userService.deleteUserFromCourse(course, user);
        Assert.assertTrue(userService.findUsersByCourseId(1).isEmpty());
    }

}

