package com.iplay.iplayapplication.UI.Media;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.mActivity.MyActivity;

/**
 * Created by admin on 2017/6/22.
 */

public class FullScreenPicActivity extends MyActivity {

    private static final String SINGLE_PHOTO_KEY = "single_photo";

    private ImageView full_pic;

    private int width;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_screen_pic_layout);

        full_pic = (ImageView) findViewById(R.id.full_screen_pic);
        String fileName = getIntent().getStringExtra(SINGLE_PHOTO_KEY);
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        full_pic.setImageBitmap(bitmap);
        
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;

        ViewGroup.LayoutParams lp = full_pic.getLayoutParams();
        lp.width = width;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        full_pic.setLayoutParams(lp);
        full_pic.setMaxWidth(width);
        full_pic.setMaxHeight(3*width);
    }

    public static void start(Context context,String fileName){
        Intent intent = new Intent(context,FullScreenPicActivity.class);
        intent.putExtra(SINGLE_PHOTO_KEY,fileName);
        context.startActivity(intent);
    }
}
