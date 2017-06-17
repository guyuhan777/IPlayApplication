package com.iplay.iplayapplication.adapter.nActivityAdapter.activityHolder;

import android.view.View;
import android.widget.ImageView;

import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/6/12.
 */

public class NormalActivityViewHolder extends BasicViewHolder {

    public final ImageView photoImage;

    public NormalActivityViewHolder(View itemView) {
        super(itemView);
        photoImage = (ImageView) itemView.findViewById(R.id.activity_main_photo);
    }
}
