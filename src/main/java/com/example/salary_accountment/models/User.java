package com.example.salary_accountment.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
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
    @JoinColumn(name="authorizationId", referencedColumnName = "authorizationId")
    private AuthorizationData authorizationData;

    @OneToMany
    private List<TimeSheet> timeSheets;

    @OneToMany
    private List<Activity> activity;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public AuthorizationData getAuthorizationData() {
        return authorizationData;
    }

    public void setAuthorizationData(AuthorizationData authorizationData) {
        this.authorizationData = authorizationData;
    }

    public List<TimeSheet> getTimeSheets() {
        return timeSheets;
    }

    public void setTimeSheets(List<TimeSheet> timeSheets) {
        this.timeSheets = timeSheets;
    }
    public User (){

    }
    public User(Integer userid, String firstName, String lastName, String patronymic, String mail, String phone, String birthday, String startDate) {
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.mail = mail;
        this.phone = phone;
        this.birthday = birthday;
        this.startDate = startDate;
    }

    public User(Integer userid, String firstName, String lastName, String patronymic, String mail, String phone, String birthday, String startDate, Post post, AuthorizationData authorizationData) {
        this.userid = userid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.mail = mail;
        this.phone = phone;
        this.birthday = birthday;
        this.startDate = startDate;
        this.post = post;
        this.authorizationData = authorizationData;
    }
}
