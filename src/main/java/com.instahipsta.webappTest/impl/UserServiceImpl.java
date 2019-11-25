package com.instahipsta.webappTest.impl;

import com.instahipsta.webappTest.domain.*;
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
    private ScheduleServiceImpl scheduleService;

    @Autowired
    private MessageWithActivationKey messageWithActivationKey;

    @Autowired
    private UtilServiceImpl utilService;

    @Value("${my_hostname}")
    private String hostname;

    @Override
    public User findUserById(long userId) {
        if (userRepo.findById(userId).isPresent()) {
            return userRepo.findById(userId).get();
       }
        else return null;
    }

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

            String jsonMessage = utilService.objectToJson(messageWithActivationKey);

            rabbitTemplate.convertAndSend("mail", jsonMessage);

            return jsonMessage;
        }
        else return null;
    }

    @Override
    public boolean activateUser(String code) {
        User user = userRepo.findByActivationCode(code);
        if (user == null) return false;
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
    public List<User> findByLastNameAndFirstName(String lastName, String firstName) {
        return userRepo.findByLastNameAndFirstName(lastName, firstName);
    }

    @Override
    public void changeEmail(User user, String email) {
        user.setEmail(email);
        user.setActivationCode(UUID.randomUUID().toString());

        userRepo.save(user);

        sendMessageWithActivationCode(user);
    }

    @Override
    public boolean isEmailAlreadyUse(String email) {
        return userRepo.isEmailAlreadyUse(email) > 0;
    }

    @Override
    public List<User> getUserListFromStringWithIds(String usersId) {
        List<User> users = new ArrayList<>();
        String[] ids = usersId.replaceAll(" ", "").split(",");
        asList(ids).forEach(idStr -> {
            long id = Long.parseLong(idStr);
            users.add(findUserById(id));
        });
        return users;
    }

    @Override
     public List<User> getUserListFromStringWithName(String userName) throws WrongNumberArgsException {
        String[] fullName = userName.split(" ");
        if (fullName.length != 2) {
            throw new WrongNumberArgsException("Needed 2 arguments, expected " + fullName.length);
        } else {
            return findByLastNameAndFirstName(fullName[0], fullName[1]);
        }
    }

    @Override
    public void deleteUserFromCourse(Course course, User user) {
        user.getCourses().remove(course);
        userRepo.save(user);
    }

    @Override
    public User save(User user) {
        return userRepo.save(user);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);
    }

    @Override
    public void changePhone(User user, String phone) {
        user.setPhone(phone);
        userRepo.save(user);
    }

    @Override
    public void changeFirstName(User user, String firstName) {
        user.setFirstName(firstName);
        userRepo.save(user);
    }

    @Override
    public void changeSecondName(User user, String secondName) {
        user.setSecondName(secondName);
        userRepo.save(user);
    }

    @Override
    public void changeLastName(User user, String lastName) {
        user.setLastName(lastName);
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(username);
        return userRepo.findByUsername(username);
    }

    @Override
    public Map<User, Set<Schedule>> findUsersSchedule(Course course) {
        Map<User, Set<Schedule>> usersSchedule = new HashMap<>();
        long courseId = course.getId();
        findUsersByCourseId(course.getId())
                .forEach(user ->
                        usersSchedule.put(user, scheduleService.getScheduleByUserAndCourseId(user.getId(), courseId)));
        return usersSchedule;
    }

    @Override
    public void delete(User user) {
        scheduleService.deleteAllScheduleForUser(user);
        userRepo.delete(user);
    }

    @Override
    public Double scoreConverter(Score score) {
        double point = 0;
        switch (score) {
            case AA:
                point = 5.5;
                break;
            case A:
                point = 5;
                break;
            case B:
                point = 4;
                break;
            case C:
                point = 3.5;
                break;
            case D:
                point = 3;
                break;
            case F:
                point = 2;
                break;
        }
        return point;
    }

    @Override
    public Double averageScore(List<Score> scores) {
        double avg = 0.0;
        for(Score score : scores) avg += scoreConverter(score);
        return avg /= scores.size();
    }

}
