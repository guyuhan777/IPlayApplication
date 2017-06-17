package com.iplay.iplayapplication.adapter.nActivityAdapter.commonComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.UI.HomePage.Remark.RemarkActivity;

/**
 * Created by admin on 2017/6/14.
 */

public class ActivityMainFunctionPart extends RelativeLayout{

    public Context mContext;

    public ImageView remark_button;

    public ActivityMainFunctionPart(Context context) {
        super(context);
    }

    public ActivityMainFunctionPart(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        LayoutInflater.from(context).inflate(R.layout.activity_main_function_bar,this,true);
        remark_button = (ImageView) findViewById(R.id.remark_button);
        remark_button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                RemarkActivity.start(mContext);
            }
        });
    }


}
