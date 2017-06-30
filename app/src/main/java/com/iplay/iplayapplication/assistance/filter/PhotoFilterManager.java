package com.iplay.iplayapplication.assistance.filter;

import android.content.Context;
import android.util.Log;

import com.iplay.iplayapplication.assistance.imageHelper.ImageHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/19.
 */

public class PhotoFilterManager {

    private static final String TAG = "PhotoFilterMananger";

    private static List<PhotoFilterItem> filterThumbs = new ArrayList<PhotoFilterItem>(10);
    private static List<PhotoFilterItem> processedThumbs = new ArrayList<PhotoFilterItem>(10);

    private PhotoFilterManager() {
    }

    public static void addThumb(PhotoFilterItem photoFilterItem) {
        filterThumbs.add(photoFilterItem);
    }

    public static List<PhotoFilterItem> processThumbs(Context context) {
        for (PhotoFilterItem thumb : filterThumbs) {
            // scaling down the image
            /*float image_width = 200 ;
            float scale = image_width / thumb.image.getWidth();
            float image_height = thumb.image.getHeight() * scale;
            Log.d(TAG,"image_width : " + image_width + " img_height : " + image_height);*/
            thumb.image = ImageHelper.autoFitSquareBitmap(thumb.image,100);
            thumb.image = thumb.filter.processFilter(thumb.image);
            if(thumb.image!=null) {
                Log.d(TAG, "filter success");
            }
            //cropping circle
            //thumb.image = GeneralUtils.generateCircularBitmap(thumb.image);
            processedThumbs.add(thumb);
        }
        return processedThumbs;
    }

    public static void clearThumbs() {
        filterThumbs = new ArrayList<>();
        processedThumbs = new ArrayList<>();
    }

}
