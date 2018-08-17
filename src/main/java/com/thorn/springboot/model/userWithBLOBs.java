package com.thorn.springboot.model;

import java.util.Date;

public class userWithBLOBs extends user {
    private String attention;

    private String history;

    private String collections;

    public userWithBLOBs(String username, String password, String name, Integer age, String
            gender, String identity, Integer level, String photo, Date regdate, Integer fans,
                         Integer myPostnum, String attention, String history, String collections) {
        super(username, password, name, age, gender, identity, level, photo, regdate, fans, myPostnum);
        this.attention = attention;
        this.history = history;
        this.collections = collections;
    }

    public userWithBLOBs() {
        super();
    }

    public String getAttention() {
        return attention;
    }

    public void setAttention(String attention) {
        this.attention = attention == null ? null : attention.trim();
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history == null ? null : history.trim();
    }

    public String getCollections() {
        return collections;
    }

    public void setCollections(String collections) {
        this.collections = collections == null ? null : collections.trim();
    }
}