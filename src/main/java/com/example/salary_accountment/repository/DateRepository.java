package com.example.salary_accountment.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.*;

public interface DateRepository extends CrudRepository<Date,Integer>{

    @Query("select u from Date u where u.month=:#{#month} and u.year=:#{#year}")
    Iterable<Date> findByMonthAndYear(String month, int year);

    @Query("select u from Date u where u.month=:#{#month}")
    Iterable<Date> findByMonth(String month);

    @Query("select u from Date u where u.year=:#{#year}")
    Iterable<Date> findByYear(int year);

    @Query("select max(dateId) from Date")
    int findTheBiggestId();
}
