package com.iplay.iplayapplication.assistance;

import android.graphics.Bitmap;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by admin on 2017/6/18.
 */

public class BitmapHolder {

    private static ConcurrentHashMap<String,Bitmap> map = new ConcurrentHashMap<>();

    public static Bitmap get(String key){
        return map.get(key);
    }

    public static void set(String key,Bitmap value){
        map.put(key,value);
    }

}
