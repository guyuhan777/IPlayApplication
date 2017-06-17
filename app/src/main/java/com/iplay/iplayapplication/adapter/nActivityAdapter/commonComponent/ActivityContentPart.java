package com.iplay.iplayapplication.adapter.nActivityAdapter.commonComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/6/14.
 */

public class ActivityContentPart extends LinearLayout {

    private Context mContext;

    public ActivityContentPart(Context context) {
        super(context);
    }

    public ActivityContentPart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.activity_content_layout,this,true);
    }
}
