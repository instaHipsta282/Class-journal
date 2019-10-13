package com.instahipsta.webappTest.repos;

import com.instahipsta.webappTest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "SELECT * " +
            "FROM usr u " +
            "WHERE u.id IN (" +
            "SELECT user_id " +
            "FROM course_usr " +
            "WHERE course_id = ?1" +
            ")", nativeQuery = true)
    List<User> findUsersByCourseId(long id);

    User findByUsername(String username);

    User findByActivationCode(String code);

    @Override
    void deleteById(Long aLong);
}
