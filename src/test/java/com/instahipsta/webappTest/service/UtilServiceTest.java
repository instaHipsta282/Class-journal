package com.instahipsta.webappTest.service;

import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.impl.CourseServiceImpl;
import com.instahipsta.webappTest.impl.ScheduleServiceImpl;
import com.instahipsta.webappTest.impl.UserServiceImpl;
import com.instahipsta.webappTest.impl.UtilServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource("/application-test.properties")
public class UtilServiceTest {

    @Autowired
    UtilServiceImpl utilService;

    @Autowired
    private CourseServiceImpl courseService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private ScheduleServiceImpl scheduleService;

    @Test
    public void stringToLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate testDate = LocalDate.parse("2019-03-08", formatter);
        LocalDate date = utilService.stringToLocalDate("2019-03-08");
        Assert.assertEquals(testDate, date);
    }

    @Test
    public void checkIdentityPassword() {
        User user = userService.findUserById(1L);
        Model model = new ExtendedModelMap();
        boolean isOk = utilService
                .checkPassword("$2a$08$PZ.ZH2I4rUszoX/FYXZZte./1IogTXOiMpZVHKdgLM/nSKnsur9Wm", user, model);
        Assert.assertTrue(model.containsAttribute("passwordError"));
        Assert.assertFalse(isOk);
    }

    @Test
    public void checkNormalPassword() {
        User user = userService.findUserById(1L);
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.checkPassword("123", user, model);
        Assert.assertTrue(isOk);
    }

    @Test
    public void checkNewPasswordReFail() {
        String newPass = "123";
        String newPassRe = "1236";
        String pass = "567";
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.checkNewPassword(newPass, newPassRe, pass, model);
        Assert.assertTrue(model.containsAttribute("newPasswordError"));
        Assert.assertFalse(isOk);
    }

    @Test
    public void checkNewPasswordIdentity() {
        String newPass = "123";
        String newPassRe = "123";
        String pass = "123";
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.checkNewPassword(newPass, newPassRe, pass, model);
        Assert.assertTrue(model.containsAttribute("newPasswordError"));
        Assert.assertFalse(isOk);
    }

    @Test
    public void checkNewPassword() {
        String newPass = "123";
        String newPassRe = "123";
        String pass = "1234";
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.checkNewPassword(newPass, newPassRe, pass, model);
        Assert.assertTrue(isOk);
    }

    @Test
    public void checkEmailSame() {
        User user = userService.findUserById(1L);
        String newEmail = user.getEmail();
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.checkNewEmail(user, newEmail, model);
        Assert.assertTrue(model.containsAttribute("newEmailError"));
        Assert.assertFalse(isOk);
    }

    @Test
    public void checkEmail() {
        User user = userService.findUserById(1L);
        String newEmail = "asasa@mail.su";
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.checkNewEmail(user, newEmail, model);
        Assert.assertTrue(isOk);
    }

    @Test
    public void checkPhoneSame() {
        User user = userService.findUserById(1L);
        String newPhone = user.getPhone();
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.checkNewPhone(user, newPhone, model);
        Assert.assertTrue(model.containsAttribute("oldPhoneError"));
        Assert.assertFalse(isOk);
    }

    @Test
    public void checkPhone() {
        User user = userService.findUserById(1L);
        String newPhone = "987654321";
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.checkNewPhone(user, newPhone, model);
        Assert.assertTrue(isOk);
    }

    @Test
    public void captchaCheck() throws Exception {
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.captchaCheck("https://www.google.com/recaptcha/api/siteverify?" +
                        "secret=6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe&" +
                        "response=6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI",
                model);
        Assert.assertTrue(isOk);
    }

    @Test
    public void captchaCheckFail() throws Exception {
        Model model = new ExtendedModelMap();
        boolean isOk = utilService.captchaCheck("https://www.google.com/recaptcha/api/siteverify?" +
                        "secret=kakayato&" +
                        "response=neudacha",
                model);
        Assert.assertFalse(isOk);
        Assert.assertTrue(model.containsAttribute("captchaError"));
    }

    @Test
    public void formCaptcha() throws Exception {
        String resp = "udacha";
        String answer = "https://www.google.com/recaptcha/api/siteverify?" +
                "secret=6LeIxAcTAAAAAGG-vFI1TnRWxMZNFuojJ4WifJWe&response=udacha";
        Assert.assertEquals(answer, utilService.formCaptcha(resp));
    }

    @Test
    public void percentCounter() {
        Assert.assertEquals(93.33333333333334, utilService.percentCounter(60, 56), 1);
    }

    @Test
    public void doubleToString() {
        Assert.assertEquals("93.3", utilService.doubleToString(93.3));
        Assert.assertEquals("93.35", utilService.doubleToString(93.3536, 2));
    }

}
