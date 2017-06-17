package com.iplay.iplayapplication.entity;

/**
 * Created by admin on 2017/5/25.
 */

public class SelfInfoFunction {
    public static int CHECK_SELF_SPORTS = 0;

    private int type;

    private int imgSrcId;

    private String title;

    public SelfInfoFunction(int type, int imgSrcId, String title) {
        this.type = type;
        this.imgSrcId = imgSrcId;
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public int getImgSrcId() {
        return imgSrcId;
    }

    public String getTitle() {
        return title;
    }
}
