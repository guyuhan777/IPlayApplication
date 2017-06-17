package com.iplay.iplayapplication.adapter.nActivityAdapter.commonComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/6/15.
 */

public class ActivityVideoPart extends FrameLayout {

    private Context mContext;

    public ImageView firstFrame;

    public ImageView video_start_button;

    public ActivityVideoPart(Context context) {
        super(context);
    }

    public ActivityVideoPart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.activity_video_part_layout,this,true);
        firstFrame = (ImageView) findViewById(R.id.activity_video_first_frame);
        video_start_button = (ImageView) findViewById(R.id.activity_video_start);
        Glide.with(context).load(R.drawable.play90_1).into(video_start_button);
        video_start_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                
            }
        });
    }
}
