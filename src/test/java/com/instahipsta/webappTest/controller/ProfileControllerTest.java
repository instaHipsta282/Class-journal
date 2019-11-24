package com.instahipsta.webappTest.controller;

import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private Map<String, Object> csrf;
    private String csrfString;
    private String testPass = "$2a$08$PZ.ZH2I4rUszoX/FYXZZte./1IogTXOiMpZVHKdgLM/nSKnsur9Wm";
    private String testEmail = "test@gmail.ru";
    private String testPhone = "89999999999";
    private String testFirstName = "Smith";
    private String testSecondName = "";
    private String testLastName = "Mr.";

    @Before
    public void init() {
        this.csrf = new HashMap<>();
        this.csrf.put("parameterName", "_csrf");
        this.csrf.put("token", csrf());
        this.csrfString = this.csrf.values().iterator().next().toString();
    }


    @Test
    @WithUserDetails("USER")
    public void getProfileWithAuthTest() throws Exception {
        this.mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Edit your security information")));
    }

    @Test
    public void getProfileWithoutAuthTest() throws Exception {
        this.mockMvc.perform(get("/profile"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails("USER")
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changePasswordWithAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changePassword")
                .sessionAttr("_csrf", this.csrf)
                .param("oldPassword", "123")
                .param("newPassword", "1234")
                .param("newPasswordRe", "1234")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        User user = userService.findUserById(2L);
        Assert.assertNotEquals(testPass, user.getPassword());
    }

    @Test
    @WithUserDetails("ADMIN")
    public void changePasswordWithAuthIncorrectPasswordTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        boolean isOk = this.mockMvc.perform(multipart("/profile/changePassword")
                .sessionAttr("_csrf", this.csrf)
                .param("oldPassword", "123434")
                .param("newPassword", "123")
                .param("newPasswordRe", "123")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status()
                        .is2xxSuccessful())
                .andReturn()
                .getModelAndView()
                .toString().contains("The password is failed");
        Assert.assertTrue(isOk);

        User user = userService.findUserById(2L);
        Assert.assertEquals(testPass, user.getPassword());
    }

    @Test
    @WithUserDetails("ADMIN")
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void changePasswordWithAuthIncorrectRePasswordTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        boolean isOk = this.mockMvc.perform(multipart("/profile/changePassword")
                .sessionAttr("_csrf", this.csrf)
                .param("oldPassword", "123")
                .param("newPassword", "1234")
                .param("newPasswordRe", "12r3")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getModelAndView()
                .toString().contains("The new password and new repeated password fields are different");
        Assert.assertTrue(isOk);

        User user = userService.findUserById(2L);
        Assert.assertEquals(testPass, user.getPassword());
    }

    @Test
    @WithUserDetails("ADMIN")
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void changePasswordWithAuthSamePasswordsTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        boolean isOk = this.mockMvc.perform(multipart("/profile/changePassword")
                .sessionAttr("_csrf", this.csrf)
                .param("oldPassword", "1234")
                .param("newPassword", "1234")
                .param("newPasswordRe", "1234")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andReturn()
                .getModelAndView()
                .toString().contains("Your new password cannot be equals with old password");
        Assert.assertTrue(isOk);

        User user = userService.findUserById(2L);
        Assert.assertEquals(testPass, user.getPassword());
    }

    @Test(expected = NestedServletException.class)
    public void changePasswordWithoutAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changePassword")
                .sessionAttr("_csrf", this.csrf)
                .param("oldPassword", "1234")
                .param("newPassword", "123")
                .param("newPasswordRe", "123")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("USER")
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeEmailWithAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changeEmail")
                .sessionAttr("_csrf", this.csrf)
                .param("newEmail", "test@test.test")
                .param("password", "123")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        User user = userService.findUserById(2L);
        Assert.assertNotEquals(testEmail, user.getEmail());
    }

    @Test
    @WithUserDetails("USER")
    public void changeEmailWithAuthIncorrectPasswordTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        boolean isOk = this.mockMvc.perform(multipart("/profile/changeEmail")
                .sessionAttr("_csrf", this.csrf)
                .param("newEmail", "test@test.test")
                .param("password", "1234")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status()
                        .is2xxSuccessful())
                .andReturn()
                .getModelAndView()
                .toString().contains("You have some email error");
        Assert.assertTrue(isOk);

        User user = userService.findUserById(2L);
        Assert.assertEquals(testEmail, user.getEmail());
    }

    @Test(expected = NestedServletException.class)
    public void changeEmailWithoutAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changeEmail")
                .sessionAttr("_csrf", this.csrf)
                .param("newEmail", "test@test.test")
                .param("password", "123")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test(expected = NestedServletException.class)
    public void changePhoneWithoutAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changePhone")
                .sessionAttr("_csrf", this.csrf)
                .param("newPhone", "0000000000")
                .param("password", "123")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("USER")
    public void changePhoneWithAuthIncorrectPasswordTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        boolean isOk = this.mockMvc.perform(multipart("/profile/changePhone")
                .sessionAttr("_csrf", this.csrf)
                .param("newPhone", "0000000000")
                .param("password", "1234")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status()
                        .is2xxSuccessful())
                .andReturn()
                .getModelAndView()
                .toString().contains("You have some phone error");
        Assert.assertTrue(isOk);

        User user = userService.findUserById(2L);
        Assert.assertEquals(testPhone, user.getPhone());
    }

    @Test
    @WithUserDetails("USER")
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changePhoneWithAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changePhone")
                .sessionAttr("_csrf", this.csrf)
                .param("newPhone", "0000000000")
                .param("password", "123")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        User user = userService.findUserById(2L);
        Assert.assertNotEquals(testPhone, user.getPhone());
    }

    @Test
    @WithUserDetails("USER")
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeOnlyFirstNameWithAut() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changeName")
                .sessionAttr("_csrf", this.csrf)
                .param("firstName", "Stepan")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        User user = userService.findUserById(2L);
        Assert.assertNotEquals(testFirstName, user.getFirstName());
    }

    @Test
    @WithUserDetails("USER")
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeOnlySecondNameWithAut() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changeName")
                .sessionAttr("_csrf", this.csrf)
                .param("secondName", "Gennadievich")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        User user = userService.findUserById(2L);
        Assert.assertNotEquals(testSecondName, user.getSecondName());
    }

    @Test
    @WithUserDetails("USER")
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeNamesWithAut() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changeName")
                .sessionAttr("_csrf", this.csrf)
                .param("firstName", "Stepan")
                .param("secondName", "Gennadievich")
                .param("lastName", "Fomichev")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());

        User user = userService.findUserById(2L);
        Assert.assertNotEquals(testFirstName, user.getFirstName());
        Assert.assertNotEquals(testSecondName, user.getSecondName());
        Assert.assertNotEquals(testLastName, user.getLastName());
    }

    @Test(expected = NestedServletException.class)
    public void changeNamesWithoutAut() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/changeName")
                .sessionAttr("_csrf", this.csrf)
                .param("firstName", "Stepan")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test(expected = NestedServletException.class)
    public void addCourseWithoutAuth() throws Exception {
        MultiValueMap<String, String> courses = new LinkedMultiValueMap<>();
        courses.put("1", Collections.singletonList("firstCourse"));
        courses.put("2", Collections.singletonList("secondCourse"));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/addCourse")
                .sessionAttr("_csrf", this.csrf)
                .params(courses)
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));
    }

    @Test
    @WithUserDetails("USER")
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addCourseWithAuth() throws Exception {
        MultiValueMap<String, String> courses = new LinkedMultiValueMap<>();
        courses.put("1", Collections.singletonList("firstCourse"));
        courses.put("3", Collections.singletonList("secondCourse"));

        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/profile/addCourse")
                .sessionAttr("_csrf", this.csrf)
                .params(courses)
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile"));

        User user = userService.findUserById(2L);
        Assert.assertEquals(2, user.getCourses().size());
    }
}
