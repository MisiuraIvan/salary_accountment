package com.example.salary_accountment.repository;

import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.User;

public interface UserRepository extends CrudRepository<User,Integer> {
}
