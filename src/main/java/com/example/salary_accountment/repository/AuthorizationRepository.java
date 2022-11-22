package com.example.salary_accountment.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.*;

public interface AuthorizationRepository extends CrudRepository<AuthorizationData,Integer>{
    @Query("select u from AuthorizationData u order by u.authorizationId desc")
    Iterable<AuthorizationData> findAllSort();

    @Query("select max(authorizationId) from AuthorizationData")
    int findTheBiggestSort();
}
