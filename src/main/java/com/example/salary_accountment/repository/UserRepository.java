package com.example.salary_accountment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("select u from User u where u.authorizationData.login = :#{#login} and u.authorizationData.password = :#{#password}")
    Optional<User> findUserByLoginAndPassword(String login, String password);
}
