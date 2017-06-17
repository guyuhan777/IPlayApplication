package com.iplay.iplayapplication.customComponent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.AdapterView;
import android.widget.ListView;

import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/5/25.
 */

public class RadiusCornerBottomSheet extends ListView {

    public RadiusCornerBottomSheet(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RadiusCornerBottomSheet(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RadiusCornerBottomSheet(Context context) {
        super(context);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                int itemnum = pointToPosition(x,y);
                if(itemnum == AdapterView.INVALID_POSITION){
                    break;
                }else{
                    if(itemnum == 0){
                        setSelector(R.drawable.app_list_corner_round_up);
                    }else{
                        setSelector(R.drawable.app_list_corner_round_center);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
