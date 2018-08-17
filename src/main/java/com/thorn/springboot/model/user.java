package com.thorn.springboot.model;

import java.util.Date;

public class user {
    private String username;

    private String password;

    private String name;

    private Integer age;

    private String gender;

    private String identity;

    private Integer level;

    private String photo;

    private Date regdate;

    private Integer fans;

    private Integer myPostnum;

    public user(String username, String name, String photo, Integer myPostnum) {
        this.username = username;
        this.name = name;
        this.photo = photo;
        this.myPostnum = myPostnum;
    }

    public user(String username, String password, String name, Integer age, String gender, String identity, Integer level, String photo, Date regdate, Integer fans, Integer myPostnum) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.identity = identity;
        this.level = level;
        this.photo = photo;
        this.regdate = regdate;
        this.fans = fans;
        this.myPostnum = myPostnum;
    }

    public user() {
        super();
    }

    public Integer getMyPostnum() {

        return myPostnum;
    }

    public void setMyPostnum(Integer myPostnum) {
        this.myPostnum = myPostnum;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity == null ? null : identity.trim();
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo == null ? null : photo.trim();
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

}