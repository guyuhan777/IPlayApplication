package com.iplay.iplayapplication.adapter.nActivityAdapter.activityHolder;

import android.view.View;
import android.widget.ImageView;

import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/6/15.
 */

public class VideoActivityViewHolder extends BasicViewHolder {

    public ImageView firstFrame;

    public VideoActivityViewHolder(View itemView) {
        super(itemView);
        firstFrame = (ImageView) itemView.findViewById(R.id.activity_video_first_frame);
    }
}
