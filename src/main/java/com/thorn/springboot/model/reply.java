package com.thorn.springboot.model;

import java.util.Date;

public class reply {
    private Integer postid;

    private Integer floor;

    private Integer floorex;

    private String replyer;

    private Date replytime;

    private Integer lastfloor;

    private String content;

    private user user;

    public reply(Integer postid, Integer floor, Integer floorex, String replyer, Date replytime, Integer lastfloor, String content) {
        this.postid = postid;
        this.floor = floor;
        this.floorex = floorex;
        this.replyer = replyer;
        this.replytime = replytime;
        this.lastfloor = lastfloor;
        this.content = content;
    }

    public reply() {
        super();
    }

    public com.thorn.springboot.model.user getUser() {
        return user;
    }

    public void setUser(com.thorn.springboot.model.user user) {
        this.user = user;
    }

    public Integer getPostid() {
        return postid;
    }

    public void setPostid(Integer postid) {
        this.postid = postid;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getFloorex() {
        return floorex;
    }

    public void setFloorex(Integer floorex) {
        this.floorex = floorex;
    }

    public String getReplyer() {
        return replyer;
    }

    public void setReplyer(String replyer) {
        this.replyer = replyer == null ? null : replyer.trim();
    }

    public Date getReplytime() {
        return replytime;
    }

    public void setReplytime(Date replytime) {
        this.replytime = replytime;
    }

    public Integer getLastfloor() {
        return lastfloor;
    }

    public void setLastfloor(Integer lastfloor) {
        this.lastfloor = lastfloor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}