package com.example.salary_accountment.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.*;

import java.util.Optional;

public interface SalaryRepository extends CrudRepository<Salary,Integer>{
    @Query("select u from Salary u where u.timeSheet.date.month=:#{#month} and u.timeSheet.date.year=:#{#year} and u.timeSheet.user.lastName=:#{#lastName}")
    Iterable<Salary> findByLastNameAndMonthAndYear(String lastName, String month, int year);

    @Query("select u from Salary u where u.timeSheet.date.month=:#{#month} and u.timeSheet.date.year=:#{#year}")
    Iterable<Salary> findByMonthAndYear(String month, int year);

    @Query("select u from Salary u where u.timeSheet.date.year=:#{#year} and u.timeSheet.user.lastName=:#{#lastName}")
    Iterable<Salary> findByLastNameAndYear(String lastName, int year);

    @Query("select u from Salary u where u.timeSheet.date.month=:#{#month} and u.timeSheet.user.lastName=:#{#lastName}")
    Iterable<Salary> findByLastNameAndMonth(String lastName, String month);

    @Query("select u from Salary u where u.timeSheet.date.month=:#{#month}")
    Iterable<Salary> findByMonth(String month);

    @Query("select u from Salary u where u.timeSheet.date.year=:#{#year}")
    Iterable<Salary> findByYear(int year);

    @Query("select u from Salary u where u.timeSheet.user.lastName=:#{#lastName}")
    Iterable<Salary> findByLastName(String lastName);

    @Query("select max(salaryId) from Salary ")
    int BiggestId();

    @Query("select u from Salary u where u.activity = :#{#activity} and u.timeSheet = :#{#timesheet}" )
    Optional<Salary> findByActivityAndTimeSheet(Activity activity, TimeSheet timesheet);
}
