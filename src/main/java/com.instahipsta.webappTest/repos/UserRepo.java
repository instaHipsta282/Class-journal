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

    @Query(value = "SELECT * " +
            "FROM usr u " +
            "WHERE u.last_name = ?1 " +
            "AND u.first_name = ?2", nativeQuery = true)
    List<User> findByLastNameAndFirstName(String lastName, String firstName);

    @Query(value = "SELECT count(*) " +
            "FROM usr u " +
            "WHERE u.email = ?1", nativeQuery = true)
    Integer isEmailAlreadyUse(String email);

    @Override
    void deleteById(Long aLong);

}
