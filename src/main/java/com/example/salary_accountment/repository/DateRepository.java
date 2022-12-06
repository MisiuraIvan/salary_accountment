package com.example.salary_accountment.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.*;

import java.util.Optional;

public interface DateRepository extends CrudRepository<Date,Integer>{

    @Query("select u from Date u where u.month=:#{#month} and u.year=:#{#year}")
    Optional<Date> findByMonthAndYear(String month, int year);

    @Query("select u from Date u where u.month=:#{#month}")
    Optional<Date> findByMonth(String month);

    @Query("select u from Date u where u.year=:#{#year}")
    Optional<Date> findByYear(int year);

    @Query("select max(dateId) from Date")
    int findTheBiggestId();
}
