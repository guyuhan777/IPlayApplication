package com.iplay.iplayapplication.assistance.imageHelper;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

/**
 * Created by admin on 2017/6/24.
 */

public class ImageHelper {

    private static final String TAG = "ImageHelper";

    public static final int TYPE_WIDTH_FIT = 0;
    public static final int TYPE_HEIGHT_FIT = 1;

    private ImageHelper(){

    }

    public static Bitmap autoFitSquareBitmap(Bitmap bitmap,int width){
        Bitmap temp = autoFitSquareBitmap(bitmap);
        int originWidth = temp.getWidth();
        float scale = (float)width / (float)originWidth;
        Log.d(TAG,"originWidth : " + originWidth + " nowWidth : " + width);
        Matrix matrix = new Matrix();
        matrix.postScale(scale,scale);
        return Bitmap.createBitmap(temp,0,0,originWidth,originWidth,matrix,true);
    }

    public static int getBitmapType(Bitmap bitmap){
        int height = bitmap.getHeight();
        int width = bitmap.getWidth();
        int type = TYPE_HEIGHT_FIT;
        if(height >= width){
            type = TYPE_WIDTH_FIT;
        }
        return type;
    }

    public static Bitmap autoFitSquareBitmap(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        if(width == height){
            Log.d(TAG,"width == height " + width + " : " + height);
            return bitmap;
        }
        if(width > height){
            Log.d(TAG,"width > height " + width + " : " + height);
            return Bitmap.createBitmap(bitmap,0,0,height,height);
        }else{
            Log.d(TAG,"width < height " + width + " : " + height);
            return Bitmap.createBitmap(bitmap,0,0,width,width);
        }
    }

    public static Bitmap getScaledBitmap(Bitmap bitmap,int screenWidth){
        int type = getBitmapType(bitmap);
        return getScaledBitmap(bitmap,screenWidth,type);
    }

    public static Bitmap getScaledBitmap(Bitmap bitmap,int screenWidth,int type){
        float scale = 0;
        Matrix m = new Matrix();
        if(type == TYPE_WIDTH_FIT){
            if(bitmap.getWidth() > screenWidth) {
                scale = (float) screenWidth / (float) bitmap.getWidth();
            }else{
                scale = (float) (screenWidth - 1) / (float) bitmap.getWidth();
            }
        }else{
            if(bitmap.getHeight() > screenWidth) {
                scale = (float) screenWidth / (float) bitmap.getHeight();
            }else{
                scale = (float) (screenWidth - 1 )/ (float) bitmap.getHeight();
            }
        }
        m.postScale(scale,scale);
        return Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),m,true);

    }

    public static void autoFitBitmap2Image(ImageView resource, Bitmap bitmap,int screenWidth){
        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();
        if(bitmapHeight >= bitmapWidth){
            Bitmap bmp = getScaledBitmap(bitmap,screenWidth,TYPE_WIDTH_FIT);
            resource.setImageBitmap(bmp);
            ViewGroup.LayoutParams params = resource.getLayoutParams();
            params.width = screenWidth;
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            resource.setLayoutParams(params);
            resource.setMaxWidth(screenWidth);
            resource.setMaxHeight(3*screenWidth);
        }else{
            Bitmap bmp = getScaledBitmap(bitmap,screenWidth,TYPE_HEIGHT_FIT);
            resource.setImageBitmap(bmp);
            ViewGroup.LayoutParams params = resource.getLayoutParams();
            params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            params.height = screenWidth;
            resource.setLayoutParams(params);
            resource.setMaxHeight(screenWidth);
            resource.setMaxWidth(screenWidth*3);
        }
    }
}
