package com.thorn.springboot.model;

import java.util.Date;

public class post {
    private Integer id;

    private String title;

    private String username;

    private Integer type;

    private Integer grade;

    private Date posttime;

    private Integer postnum;

    private Date lastposttime;

    private Integer views;

    private user user;

    private Integer collectionnum;


    public post(Integer id, String title, String username, Integer type, Integer grade, Date posttime, Integer postnum, Date lastposttime, Integer views, Integer collectionnum) {
        this.id = id;
        this.title = title;
        this.username = username;
        this.type = type;
        this.grade = grade;
        this.posttime = posttime;
        this.postnum = postnum;
        this.lastposttime = lastposttime;
        this.views = views;
        this.collectionnum = collectionnum;
    }

    public post() {
        super();
    }

    public post(Integer id, String title, Integer views) {
        this.id = id;
        this.title = title;
        this.views = views;
    }

    public post(Integer id, String title, Integer postnum, Integer views) {
        this.id = id;
        this.title = title;
        this.postnum = postnum;
        this.views = views;
    }

    public Integer getCollectionnum() {
        return collectionnum;
    }

    public void setCollectionnum(Integer collectionnum) {
        this.collectionnum = collectionnum;
    }

    public com.thorn.springboot.model.user getUser() {
        return user;
    }

    public void setUser(com.thorn.springboot.model.user user) {
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {

        this.type = type;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Date getPosttime() {
        return posttime;
    }

    public void setPosttime(Date posttime) {
        this.posttime = posttime;
    }

    public Integer getPostnum() {
        return postnum;
    }

    public void setPostnum(Integer postnum) {
        this.postnum = postnum;
    }

    public Date getLastposttime() {
        return lastposttime;
    }

    public void setLastposttime(Date lastposttime) {
        this.lastposttime = lastposttime;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }
}