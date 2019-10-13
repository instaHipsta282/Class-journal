package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.User;

import java.util.List;

public interface UserService {

    List<User> findAll();

    List<User> findUsersByCourseId(long id);

    boolean addUserToDb(User user);

    void sendMessageWithActivationCode(User user);

    boolean activateUser(String code);

    void updateEmail(User user, String email);

}
