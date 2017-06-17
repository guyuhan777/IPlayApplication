package com.iplay.iplayapplication.adapter.nActivityAdapter.activityHolder;

import android.support.v4.view.ViewPager;
import android.view.View;

import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/6/14.
 */

public class MultiPhotoActivityViewHolder extends BasicViewHolder {

    public ViewPager viewPager;

    public MultiPhotoActivityViewHolder(View itemView) {
        super(itemView);
        viewPager = (ViewPager) itemView.findViewById(R.id.view_pager);
    }
}
