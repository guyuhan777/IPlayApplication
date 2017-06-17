package com.iplay.iplayapplication.adapter.nActivityAdapter.commonComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2017/6/14.
 */

public class ActivityTitlePart extends RelativeLayout {

    private Context mContext;

    public CircleImageView avatar;

    public ActivityTitlePart(Context context) {
        super(context);
    }
    
    public ActivityTitlePart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.activity_title_layout,this,true);
        avatar = (CircleImageView) findViewById(R.id.activity_user_avatar);
        Glide.with(mContext).load(R.drawable.shenzi).into(avatar);
    }
}
