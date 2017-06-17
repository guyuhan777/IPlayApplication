package com.iplay.iplayapplication.entity;

/**
 * Created by admin on 2017/6/3.
 */

public class User {
    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public int getUser_avatar() {
        return user_avatar;
    }

    public void setUser_avatar(int user_avatar) {
        this.user_avatar = user_avatar;
    }

    private String user_name;

    private int user_avatar;

}
