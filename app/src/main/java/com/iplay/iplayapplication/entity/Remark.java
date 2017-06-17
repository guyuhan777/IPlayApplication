package com.iplay.iplayapplication.entity;

/**
 * Created by admin on 2017/6/3.
 */

public class Remark {

    private User remarker;

    private String content;

    public User getRemarker() {
        return remarker;
    }

    public void setRemarker(User remarker) {
        this.remarker = remarker;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
