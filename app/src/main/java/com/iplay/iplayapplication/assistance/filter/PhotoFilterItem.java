package com.iplay.iplayapplication.assistance.filter;

import android.graphics.Bitmap;

import com.zomato.photofilters.imageprocessors.Filter;

/**
 * Created by admin on 2017/6/19.
 */

public class PhotoFilterItem {
    public Bitmap image;
    public Filter filter;

    public PhotoFilterItem() {
        image = null;
        filter = new Filter();
    }
}
