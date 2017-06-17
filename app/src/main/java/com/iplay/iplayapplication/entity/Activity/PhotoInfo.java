package com.iplay.iplayapplication.entity.Activity;

import java.util.List;

/**
 * Created by admin on 2017/6/1.
 */

public class PhotoInfo extends MediaInfo {

    private int photoId;

    private String photoUri;

    private List<String> photoUris;

    private List<Integer> photoIds;

    public List<Integer> getPhotoIds() {
        return photoIds;
    }

    public void setPhotoIds(List<Integer> photoIds) {
        this.photoIds = photoIds;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public List<String> getPhotoUris() {
        return photoUris;
    }

    public void setPhotoUris(List<String> photoUris) {
        this.photoUris = photoUris;
    }

    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }
}
