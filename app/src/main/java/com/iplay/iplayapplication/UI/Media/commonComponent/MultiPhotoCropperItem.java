package com.iplay.iplayapplication.UI.Media.commonComponent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.assistance.imageHelper.ImageHelper;
import com.iplay.iplayapplication.customComponent.autoChangeImageView.TouchImageView;

/**
 * Created by admin on 2017/6/24.
 */

public class MultiPhotoCropperItem extends RelativeLayout {

    private static final String TAG = "CropperItem";

    public TouchImageView crop_photo;

    private View view;

    public MultiPhotoCropperItem(Context context) {
        super(context);
        mContext = context;
        view = LayoutInflater.from(context).inflate(R.layout.crop_photo_pager_item,this,true);
        crop_photo = (TouchImageView) view.findViewById(R.id.multi_photo_crop_img);
    }

    private Context mContext;

    public void setImage(String filename){
        if(crop_photo!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(filename);
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.wen);
            crop_photo.setImageBitmap(bitmap);

        }else{
            Log.d(TAG,"crop_photo null");
        }
    }

    public void setImage(String filename,int screenWidth){
        if(crop_photo!=null) {
            Bitmap bitmap = BitmapFactory.decodeFile(filename);
            //Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.wen);
            //crop_photo.setImageBitmap(bitmap);
            //BitmapHolder.set(filename,bitmap);
            ImageHelper.autoFitBitmap2Image(crop_photo,bitmap,screenWidth);
        }else{
            Log.d(TAG,"crop_photo null");
        }
    }

}
