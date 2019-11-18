package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.Schedule;
import com.instahipsta.webappTest.domain.User;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    List<User> findAll();

    List<User> findUsersByCourseId(long id);

    User findUserById(long userId);

    boolean addUser(User user);

    String sendMessageWithActivationCode(User user);

    boolean activateUser(String code);

    List<User> findByLastNameAndFirstName(String lastName, String firstName);

    void changeEmail(User user, String email);

    boolean isEmailAlreadyUse(String email);

    User save(User user);

    void changePassword(User user, String newPassword);

    void changePhone(User user, String phone);

    void changeFirstName(User user, String firstName);

    void changeSecondName(User user, String secondName);

    void changeLastName(User user, String lastName);

    Map<User, Set<Schedule>> findUsersSchedule(Course course);

}
