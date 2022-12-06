package com.example.salary_accountment.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.*;

import java.util.Optional;

public interface TimeSheetRepository extends CrudRepository<TimeSheet,Integer>{

    @Query("select u from TimeSheet u where u.date.month=:#{#month} and u.date.year=:#{#year} and u.user.lastName=:#{#lastName}")
    Iterable<TimeSheet> findByLastNameAndMonthAndYear(String lastName, String month, int year);

    @Query("select u from TimeSheet u where u.date.month=:#{#month} and u.date.year=:#{#year}")
    Iterable<TimeSheet> findByMonthAndYear(String month, int year);

    @Query("select u from TimeSheet u where u.date.year=:#{#year} and u.user.lastName=:#{#lastName}")
    Iterable<TimeSheet> findByLastNameAndYear(String lastName, int year);

    @Query("select u from TimeSheet u where u.date.month=:#{#month} and u.user.lastName=:#{#lastName}")
    Iterable<TimeSheet> findByLastNameAndMonth(String lastName, String month);

    @Query("select u from TimeSheet u where u.date.month=:#{#month}")
    Iterable<TimeSheet> findByMonth(String month);

    @Query("select u from TimeSheet u where u.date.year=:#{#year}")
    Iterable<TimeSheet> findByYear(int year);

    @Query("select u from TimeSheet u where u.user.lastName=:#{#lastName}")
    Iterable<TimeSheet> findByLastName(String lastName);

    @Query("select max(timeSheetId) from TimeSheet ")
    int findTheBiggestId();

    @Query("select u from TimeSheet u where u.user.userid=:#{#uid}")
    Iterable<TimeSheet> findByUserId(int uid);

    @Query("select u from TimeSheet u where u.date.month=:#{#month} and u.date.year=:#{#year} and u.user.userid=:#{#userid}")
    Optional<TimeSheet> findByUserIdAndMonthAndYear(Integer userid, String month, int year);
}
