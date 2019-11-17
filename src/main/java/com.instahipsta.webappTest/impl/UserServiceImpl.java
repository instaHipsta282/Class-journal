package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.JsonConverter;
import com.instahipsta.webappTest.domain.Course;
import com.instahipsta.webappTest.domain.Role;
import com.instahipsta.webappTest.domain.User;
import com.instahipsta.webappTest.messaging.tasks.MessageWithActivationKey;
import com.instahipsta.webappTest.repos.UserRepo;
import com.instahipsta.webappTest.services.UserService;
import com.sun.org.apache.xpath.internal.functions.WrongNumberArgsException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;

import static java.util.Arrays.asList;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MessageWithActivationKey messageWithActivationKey;

    @Value("${my_hostname}")
    private String hostname;

    //testing
    @Override
    public User findUserById(long userId) {
        if (userRepo.findById(userId).isPresent()) {
            return userRepo.findById(userId).get();
        }
        else return null;
    }


    //testing
    @Override
    public boolean addUser(User user) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) return false;

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setActivationCode(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepo.save(user);

        sendMessageWithActivationCode(user);

        return true;
    }

    //testing
    @Override
    public String sendMessageWithActivationCode(User user) {
        String subject = "Activation code";
        if (!StringUtils.isEmpty(user.getEmail())) {
            String message = String.format(
                    "Hello, %s! \n" +
                            "For activate your account visit next link: http://%s/activate/%s",
                    user.getUsername(),
                    hostname,
                    user.getActivationCode()
            );
            messageWithActivationKey.setEmailTo(user.getEmail());
            messageWithActivationKey.setSubject(subject);
            messageWithActivationKey.setMessage(message);

            String jsonMessage = JsonConverter.objectToJson(messageWithActivationKey);

            rabbitTemplate.convertAndSend("mail", jsonMessage);

            return jsonMessage;
        }
        else return null;
    }

    //testing
    @Override
    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null) return false;
        user.setActivationCode(null);
        userRepo.save(user);
        return true;
    }

    //testing
    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }

    //testing
    @Override
    public List<User> findUsersByCourseId(long id) {
        return userRepo.findUsersByCourseId(id);
    }

    //testing
    @Override
    public List<User> findByLastNameAndFirstName(String lastName, String firstName) {
        return userRepo.findByLastNameAndFirstName(lastName, firstName);
    }

    //testing
    @Override
    public void changeEmail(User user, String email) {
        user.setEmail(email);
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        sendMessageWithActivationCode(user);
    }

    //testing
    @Override
    public boolean isEmailAlreadyUse(String email) {
        return userRepo.isEmailAlreadyUse(email) > 0;
    }

    //testing
    public List<User> getUserListFromStringWithIds(String usersId) {
        List<User> users = new ArrayList<>();
        String[] ids = usersId.replaceAll(" ", "").split(",");
        asList(ids).forEach(idStr -> {
            long id = Long.parseLong(idStr);
            users.add(findUserById(id));
        });
        return users;
    }

     public List<User> getUserListFromStringWithName(String userName) throws WrongNumberArgsException {
        String[] fullName = userName.split(" ");
        if (fullName.length != 2) {
            throw new WrongNumberArgsException("Needed 2 arguments, expected " + fullName.length);
        } else {
            return findByLastNameAndFirstName(fullName[0], fullName[1]);
        }
    }

    //testing
    public void deleteUserFromCourse(Course course, User user) {
        user.getCourses().remove(course);
        userRepo.save(user);
    }

    //testing
    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    //testing
    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    //testing
    @Override
    public void changePhone(User user, String phone) {
        user.setPhone(phone);
        userRepo.save(user);
    }

    //testing
    @Override
    public void changeFirstName(User user, String firstName) {
        user.setFirstName(firstName);
        userRepo.save(user);
    }

    //testing
    @Override
    public void changeSecondName(User user, String secondName) {
        user.setSecondName(secondName);
        userRepo.save(user);
    }

    //testing
    @Override
    public void changeLastName(User user, String lastName) {
        user.setLastName(lastName);
        userRepo.save(user);
    }

    //testing
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }
}
