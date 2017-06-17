package com.iplay.iplayapplication.entity;

/**
 * Created by admin on 2017/5/24.
 */

public class AvatarChoice {

    public static final int CHOOSE_FROM_ALBUM = 0;

    public static final int TAKE_PHOTO = 1;

    public static final int CANCEL = 2;

    private int type;

    private String title;

    public AvatarChoice(int type,String title){
        this.type = type;
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }
}
