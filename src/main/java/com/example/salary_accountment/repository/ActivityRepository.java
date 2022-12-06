package com.example.salary_accountment.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.*;

public interface ActivityRepository extends CrudRepository<Activity,Integer>{

    @Query("select u from Activity u where u.date.month=:#{#month} and u.date.year=:#{#year} and u.user.lastName=:#{#lastName}")
    Iterable<Activity> findByLastNameAndMonthAndYear(String lastName, String month, int year);

    @Query("select u from Activity u where u.date.month=:#{#month} and u.date.year=:#{#year}")
    Iterable<Activity> findByMonthAndYear(String month, int year);

    @Query("select u from Activity u where u.date.year=:#{#year} and u.user.lastName=:#{#lastName}")
    Iterable<Activity> findByLastNameAndYear(String lastName, int year);

    @Query("select u from Activity u where u.date.month=:#{#month} and u.user.lastName=:#{#lastName}")
    Iterable<Activity> findByLastNameAndMonth(String lastName, String month);

    @Query("select u from Activity u where u.date.month=:#{#month}")
    Iterable<Activity> findByMonth(String month);

    @Query("select u from Activity u where u.date.year=:#{#year}")
    Iterable<Activity> findByYear(int year);

    @Query("select u from Activity u where u.user.lastName=:#{#lastName}")
    Iterable<Activity> findByLastName(String lastName);

    @Query("select max(activityId) from Activity ")
    int findTheBiggestId();

    @Query("select u from Activity u where u.user.userid=:#{#uid}")
    Iterable<Activity> findByUserId(int uid);
}
