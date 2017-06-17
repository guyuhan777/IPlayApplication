package com.iplay.iplayapplication.UI.SelfPage.commonComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/6/17.
 */

public class SelfInfoCirclePart extends LinearLayout {
    public SelfInfoCirclePart(Context context) {
        super(context);
    }

    public SelfInfoCirclePart(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.self_info_circle,this,true);
    }
}
