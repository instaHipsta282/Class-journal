package com.instahipsta.webappTest.services;

import com.instahipsta.webappTest.domain.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    List<User> findAll();

    List<User> findUsersByCourseId(long id);

    Optional<User> findUserById(long userId);

    boolean addUserToDb(User user);

    void sendMessageWithActivationCode(User user);

    boolean activateUser(String code);

    List<User> findByLastNameAndFirstName(String lastName, String firstName);

    void updateEmail(User user, String email);

    boolean isEmailAlreadyUse(String email);

}
