package com.example.salary_accountment.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.User;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User,Integer> {
    @Query("select u from User u where u.authorizationData.login = :#{#login} and u.authorizationData.password = :#{#password}")
    Optional<User> findUserByLoginAndPassword(String login, String password);

    @Query("select u from User u order by u.userid desc")
    Iterable<User> findAllSort();

    @Query("select u from User u where u.lastName=:#{#lastname}")
    Iterable<User> findByLastName(String lastname);
    @Query("select u from User u where u.post.post=:#{#post}")
    Iterable<User> findByPost(String post);
    @Query("select u from User u where u.post.post=:#{#post} and u.lastName=:#{#lastname}")
    Iterable<User> findByLastNameAndPost(String lastname,String post);

    @Query("select max(userid) from User")
    int findTheBiggestId();
}
