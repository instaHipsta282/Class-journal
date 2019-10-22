package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.MailSender;
import com.instahipsta.webappTest.domain.Role;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.repos.UserRepo;
import com.instahipsta.webappTest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private MailSender mailSender;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${my_hostname}")
    private String hostname;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return user;
    }

    @Override
    public boolean addUserToDb(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return false;
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessageWithActivationCode(user);

        return true;
    }

    @Override
    public void sendMessageWithActivationCode(User user) {
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "For activate your account visit next link: http://%s/activate/%s",
                    user.getUsername(),
                    hostname,
                    user.getActivationCode()
            );
            mailSender.send(user.getEmail(), "Activation code", message);
        }
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);

        if (user == null) {
            return false;
        }

        user.setActivationCode(null);

        userRepo.save(user);

        return true;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    @Override
    public List<User> findUsersByCourseId(long id) {
        return userRepo.findUsersByCourseId(id);
    }

    @Override
    public void updateEmail(User user, String email) {

        user.setEmail(email);

        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        sendMessageWithActivationCode(user);
    }

    @Override
    public boolean isEmailAlreadyUse(String email) {
        return userRepo.isEmailAlreadyUse(email) > 0;
    }


}
