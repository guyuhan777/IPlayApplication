package com.iplay.iplayapplication.UI.Media;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.assistance.BitmapHolder;
import com.iplay.iplayapplication.assistance.ImgUtils;
import com.iplay.iplayapplication.customComponent.autoChangeImageView.TouchImageView;
import com.iplay.iplayapplication.mActivity.MyActivity;

/**
 * Created by admin on 2017/6/13.
 */

public class PhotoEditActivity extends MyActivity implements View.OnClickListener {

    private static final String TAG = "PhotoEditActivity";

    private TouchImageView crop_img;

    private static final String PHOTO_KEY = "PHOTO_BYTE";

    private int width;

    private TextView edit_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_layout);
        edit_button = (TextView) findViewById(R.id.edit_button);
        edit_button.setOnClickListener(this);

        crop_img = (TouchImageView) findViewById(R.id.crop_img);

        Bitmap bitmap = BitmapHolder.get(ImgUtils.BITMAP_KEY);
        crop_img.setImageBitmap(bitmap);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;

        int screenWidth = width;
        ViewGroup.LayoutParams lp = crop_img.getLayoutParams();
        lp.width = screenWidth;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        crop_img.setLayoutParams(lp);
        crop_img.setMaxWidth(screenWidth);
        crop_img.setMaxHeight(3*screenWidth);
    }



    public static void start(Context context,String fileName){
        Intent intent = new Intent(context,PhotoEditActivity.class);
        intent.putExtra(PHOTO_KEY,fileName);
        context.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit_button:
                if(ImgUtils.saveEditImage(this,crop_img)){
                    Log.d(TAG,"Edit Success");
                }else{
                    Log.d(TAG,"Edit Failed");
                }
                break;
            default:
                break;
        }
    }
}
