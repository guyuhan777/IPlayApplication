package com.iplay.iplayapplication.UI.SelfPage;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/5/27.
 */

public class SelfInfoTitle extends RelativeLayout {

    public SelfInfoTitle(Context context) {
        super(context);
    }

    public SelfInfoTitle(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.self_info_title,this,true);
    }

    public SelfInfoTitle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
