package com.example.salary_accountment.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class AuthorizationData {
    @Id
    private Integer authorizationId;
    private String login;
    private String password;
    @OneToOne
    private User user;

    public AuthorizationData() {
    }

    public AuthorizationData(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public AuthorizationData(String login) {
        this.login = login;
    }

    public AuthorizationData(int aid, String login, String password) {
        this.authorizationId=aid;
        this.login = login;
        this.password = password;
    }

    public Integer getAuthorizationId() {
        return authorizationId;
    }

    public void setAuthorizationId(Integer authorizationId) {
        this.authorizationId = authorizationId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
