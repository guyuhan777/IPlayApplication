package com.iplay.iplayapplication.customComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.entity.SelfInfoFunction;

/**
 * Created by admin on 2017/5/26.
 */

public class FunctionBar extends LinearLayout {

    public ImageView logo;

    public TextView title;

    private SelfInfoFunction function;

    public void setData(SelfInfoFunction function){
        this.function = function;
        Glide.with(getContext()).load(function.getImgSrcId()).into(logo);
        title.setText(function.getTitle());
    }

    public FunctionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOrientation(HORIZONTAL);
        LayoutInflater.from(context).inflate(R.layout.self_info_funtion_item,this,true);
        logo = (ImageView) findViewById(R.id.sf_item_image);
        Glide.with(getContext()).load(R.drawable.yinyang).into(logo);
        title = (TextView) findViewById(R.id.sf_item_text);
    }
}
