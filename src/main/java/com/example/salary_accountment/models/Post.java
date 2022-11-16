package com.example.salary_accountment.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer postId;
    private String post;
    private int wages;
    @OneToMany
    private List<User> users;
}
