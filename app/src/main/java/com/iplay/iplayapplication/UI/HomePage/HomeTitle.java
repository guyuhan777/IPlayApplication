package com.iplay.iplayapplication.UI.HomePage;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.UI.Media.CameraActivity;

/**
 * Created by admin on 2017/5/29.
 */

public class HomeTitle extends RelativeLayout implements View.OnClickListener{

    private ImageView photo_video_button;

    public HomeTitle(Context context) {
        super(context);
    }

    public HomeTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.home_title,this,true);
        photo_video_button = (ImageView) findViewById(R.id.photo_video_take_button);
        photo_video_button.setOnClickListener(this);
    }

    public HomeTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.photo_video_take_button:
                Intent intent = new Intent(getContext(), CameraActivity.class);
                getContext().startActivity(intent);
                break;
        }
    }
}
