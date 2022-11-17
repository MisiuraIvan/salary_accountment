package com.example.salary_accountment.repository;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.*;

public interface AuthorizationRepository extends CrudRepository<AuthorizationData,Integer>{
}
