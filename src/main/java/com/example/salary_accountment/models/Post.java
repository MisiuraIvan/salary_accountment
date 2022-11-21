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

    public Post(){}

    public Post(Integer id, String post, int wages) {
        this.postId=id;
        this.post=post;
        this.wages=wages;
    }

    public Post(String post, int wages) {
        this.post=post;
        this.wages=wages;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public int getWages() {
        return wages;
    }

    public void setWages(int wages) {
        this.wages = wages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
