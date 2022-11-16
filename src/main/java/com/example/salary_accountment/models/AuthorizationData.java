package com.example.salary_accountment.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class AuthorizationData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer authorizationId;
    private String login;
    private String password;
    @OneToOne
    private User user;
}
