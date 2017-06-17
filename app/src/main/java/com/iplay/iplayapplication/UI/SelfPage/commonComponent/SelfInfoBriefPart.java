package com.iplay.iplayapplication.UI.SelfPage.commonComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2017/6/17.
 */

public class SelfInfoBriefPart extends LinearLayout {

    public CircleImageView avatar;

    public SelfInfoBriefPart(Context context) {
        super(context);
    }

    public SelfInfoBriefPart(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.self_info_brief,this,true);
        avatar = (CircleImageView) findViewById(R.id.self_info_avatar);
        Glide.with(context).load(R.drawable.shenzi).into(avatar);
    }
}
