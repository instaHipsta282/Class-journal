package com.instahipsta.webappTest.controller;

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
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class CourseControllerTest {

    private byte[] fileData;
    private String csrfString;
    private Map<String, Object> csrf;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void createMultipartFile() {
        this.csrf = new HashMap<>();
        this.csrf.put("parameterName", "_csrf");
        this.csrf.put("token", csrf());
        this.csrfString = this.csrf.values().iterator().next().toString();

        Path path = Paths.get("test-uploads/rick-y-morty.jpg");
        try { this.fileData = Files.readAllBytes(path); }
        catch (IOException e) { e.printStackTrace(); }
    }

    @Test
    @WithUserDetails("USER")
    public void courseListWithUserAuthTest() throws Exception {
        boolean isOk = this.mockMvc.perform(get("/courseList"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Begin date")))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("Course description:");
        Assert.assertFalse(isOk);
    }

    @Test
    @WithUserDetails("ADMIN")
    public void courseListWithAdminAuthTest() throws Exception {
        this.mockMvc.perform(get("/courseList"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Begin date")))
                .andExpect(content().string(containsString("Course description:")));
    }

    @Test
    public void getCourseListWithoutAuthTest() throws Exception{
        this.mockMvc.perform(get("/courseList"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails("ADMIN")
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCourseWithAdminAuthTest() throws Exception {
        this.mockMvc.perform(get("/courseList/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Students count:")))
                .andExpect(content().string(containsString("col-form-label\">Student</label>")));
    }

    @Test
    @WithUserDetails("USER")
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCourseWithUserAuthTest() throws Exception {
        boolean isOk = this.mockMvc.perform(get("/courseList/1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Students count:")))
                .andReturn()
                .getResponse()
                .getContentAsString()
                .contains("col-form-label\">Student</label>");
        Assert.assertFalse(isOk);
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void getCourseWithoutAuthTest() throws Exception{
        this.mockMvc.perform(get("/courseList/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test(expected = NestedServletException.class)
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addNewCourseWithoutAuthAllParamTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/addNewCourse")
                .file("courseImage", this.fileData)
                .sessionAttr("_csrf", this.csrf)
                .contentType("application/octet-stream")
                .param("courseTitle", "Algorithmic")
                .param("courseDescription", "Some description")
                .param("startDate", "2019-12-05")
                .param("endDate", "2019-12-11")
                .param("studentsLimit", "6")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("ADMIN")
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addNewCourseWithAuthAdminAllParamTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/addNewCourse")
                .file("courseImage", this.fileData)
                .sessionAttr("_csrf", this.csrf)
                .contentType("application/octet-stream")
                .param("courseTitle", "Algorithmic")
                .param("courseDescription", "Some description")
                .param("startDate", "2019-12-05")
                .param("endDate", "2019-12-11")
                .param("studentsLimit", "6")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test(expected = NestedServletException.class)
    @WithUserDetails("USER")
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addNewCourseWithAuthUserAllParamTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/addNewCourse")
                .file("courseImage", this.fileData)
                .sessionAttr("_csrf", this.csrf)
                .contentType("application/octet-stream")
                .param("courseTitle", "Algorithmic")
                .param("courseDescription", "Some description")
                .param("startDate", "2019-12-05")
                .param("endDate", "2019-12-11")
                .param("studentsLimit", "6")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @WithUserDetails("ADMIN")
    @Sql(value = {"/delete-course-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addNewCourseWithAuthAdminWithoutImageAndDescriptionTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/addNewCourse")
                .sessionAttr("_csrf", this.csrf)
                .param("courseTitle", "Algorithmic")
                .param("startDate", "2019-12-05")
                .param("endDate", "2019-12-11")
                .param("studentsLimit", "6")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is2xxSuccessful());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteCourseWithoutAuth() throws Exception{
        this.mockMvc.perform(get("/courseList/deleteCourse")
                .param("courseId", "1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("USER")
    public void deleteCourseWithUserAuth() throws Exception{
        this.mockMvc.perform(get("/courseList/deleteCourse")
                .param("courseId", "1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is4xxClientError());
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("ADMIN")
    public void deleteCourseWithAdminAuthTest() throws Exception{
        this.mockMvc.perform(get("/courseList/deleteCourse")
                .param("courseId", "1"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList"));
    }

    @Test(expected = NestedServletException.class)
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void changeScheduleWithoutAuthTest() throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/changeSchedule")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("user", "1")
                .param("date", "2019-11-16")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test(expected = NestedServletException.class)
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("USER")
    public void changeScheduleWithUserAuthTest() throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/changeSchedule")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("user", "1")
                .param("date", "2019-11-16")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("ADMIN")
    public void changeScheduleWithAdminAuthWithoutPrStatAndScoreTest() throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/changeSchedule")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("user", "1")
                .param("date", "2019-11-16")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("ADMIN")
    public void changeScheduleWithAdminAuthAllParamTest() throws Exception{
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/changeSchedule")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("user", "1")
                .param("date", "2019-11-16")
                .param("presenceStatus", "PRESENCE")
                .param("score", "A")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test(expected = NestedServletException.class)
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void addUserWithoutAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/addUser")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("usersId", "1,2")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test(expected = NestedServletException.class)
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("USER")
    public void addUserWithUserAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/addUser")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("usersId", "1,2")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("ADMIN")
    public void addUserWithAdminAuthWithUserIdTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/addUser")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("usersId", "2")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("ADMIN")
    public void addUserWithAdminAuthWithUserNameTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/addUser")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("userName", "Mr. Smith")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test(expected = NestedServletException.class)
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteUserFromCourseWithoutAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/addUser")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("usersId", "1")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test(expected = NestedServletException.class)
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("USER")
    public void deleteUserFromCourseWithUserAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/addUser")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("usersId", "1")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

    @Test
    @Sql(value = {"/create-course-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/delete-course-after.sql", "/delete-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @WithUserDetails("ADMIN")
    public void deleteUserFromCourseWithAdminAuthTest() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.mockMvc.perform(multipart("/courseList/1/addUser")
                .sessionAttr("_csrf", this.csrf)
                .param("course", "1")
                .param("usersId", "1")
                .param("_csrf", this.csrfString))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/courseList/1"));
    }

}
