package com.iplay.iplayapplication.message;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/21.
 */

public class MediaTypeMessage implements Serializable{

    public static final int TYPE_SINGLE_PHOTO = 1;
    public static final int TYPE_MULTIE_PHOTOS = 2;
    public static final int TYPE_VIDEO = 3;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int type;

    public String fileName;
}
