package com.example.salary_accountment.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer userid;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String mail;
    private String phone;
    private String birthday;
    private String startDate;

    @ManyToOne
    @JoinColumn(name="postId", referencedColumnName = "postId", nullable = false)
    private Post post;

    @OneToOne
    @JoinColumn(name="authorizationId", referencedColumnName = "authorizationId", nullable = false)
    private AuthorizationData authorizationData;

    @OneToMany
    private List<TimeSheet> timeSheets;
}
