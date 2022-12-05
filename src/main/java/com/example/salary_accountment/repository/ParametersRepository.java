package com.example.salary_accountment.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.*;

public interface ParametersRepository extends CrudRepository<Parameters,Integer>{
    @Query("select max(parametersId) from Parameters ")
    int BiggestId();
}
