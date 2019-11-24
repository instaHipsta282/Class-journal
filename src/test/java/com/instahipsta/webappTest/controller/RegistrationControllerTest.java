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
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
@AutoConfigureMockMvc
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private String g_recaptcha_response = "6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI";
    private Map<String, Object> csrf;
    private String csrfString;
    private byte[] fileData;

    @Before
    public void init() {
        this.csrf = new HashMap<>();
        this.csrf.put("parameterName", "_csrf");
        this.csrf.put("token", csrf());
        this.csrfString = this.csrf.values().iterator().next().toString();

        Path path = Paths.get("test-uploads/rick-y-morty.jpg");
        try { this.fileData = Files.readAllBytes(path); }
        catch (IOException e) { e.printStackTrace(); }
    }
    @Test
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void activate() throws Exception {
        this.mockMvc.perform(get("/activate/123456"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        User user = userService.findUserById(2L);
        Assert.assertNull(user.getActivationCode());
    }

    @Test
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/update-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void activateWithFalseCode() throws Exception {
        this.mockMvc.perform(get("/activate/654321"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));
        User user = userService.findUserById(2L);
        Assert.assertNotNull(user.getActivationCode());
    }

    @Test
    @WithUserDetails("USER")
    public void getRegistrationPageWithAuth() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void getRegistrationPageWithoutAut() throws Exception {
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Enter your second name")));
    }
//
//    @Test
//    public void registration() throws Exception {
//        this.mockMvc.perform(multipart("/registration")
//                .file("usersPhoto", fileData)
//                .param("g-recaptcha-response", g_recaptcha_response)
//                .param("password2", "123")
//                .param("user", "hzzhzhzhz"))
//                .andDo(print())
//                .andExpect(status().is2xxSuccessful());
//    }

}
