package com.example.salary_accountment.repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.example.salary_accountment.models.*;

import java.util.Optional;

public interface PostRepository extends CrudRepository<Post,Integer>{
    @Query("select u from Post u where u.post = :#{#post}")
    Optional<Post> findByName(String post);
}
