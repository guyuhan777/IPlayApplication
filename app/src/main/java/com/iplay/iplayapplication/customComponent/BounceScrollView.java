package com.iplay.iplayapplication.customComponent;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.ScrollView;

/**
 * Created by admin on 2017/5/28.
 */

public class BounceScrollView extends ScrollView {

    public static final String TAG = "BOUNCE_SCROLL_VIEW";

    private View inner;

    private float y;

    private Rect normal = new Rect();

    private boolean isCount = false;

    private float lastX = 0;

    private float lastY = 0;

    private float currentX = 0;

    private float currentY = 0;

    private float distanceX = 0;

    private float distanceY = 0;

    private boolean upDownSlide = false;

    public BounceScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        if(getChildCount() > 0){
            inner = getChildAt(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        currentX  = ev.getX();
        currentY = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                distanceX = currentX - lastX;
                distanceY = currentY - lastY;
                if(Math.abs(distanceX)<Math.abs(distanceY) && Math.abs(distanceY) > 12){
                    upDownSlide = true;
                }
                break;
            default:
                break;
            }
        lastX = currentX;
        lastY = currentY;

        if(upDownSlide && inner!=null){
            commOnTouchEvent(ev);
        }

        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return super.onTouchEvent(ev);
    }

    private void commOnTouchEvent(MotionEvent ev){
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_UP:
                if(isNeedAnimation()){
                    animation();
                    isCount = false;
                }
                clear0();
                break;
            case MotionEvent.ACTION_MOVE:
                final float preY = y;
                float nowY = ev.getY();
                int deltaY = (int) (preY - nowY);
                if(!isCount){
                    deltaY = 0;
                }
                y = nowY;
                if(isNeedMove()){
                    if (isNeedMove()) {
                        // 初始化头部矩形
                        if (normal.isEmpty()) {
                            // 保存正常的布局位置
                            normal.set(inner.getLeft(), inner.getTop(),
                                    inner.getRight(), inner.getBottom());
                        }
                        // 移动布局
                        inner.layout(inner.getLeft(), inner.getTop() - deltaY / 2,
                                inner.getRight(), inner.getBottom() - deltaY / 2);
                    }
                    isCount = true;
                    break;
                }
        }
    }

    public boolean isNeedAnimation(){
        return !normal.isEmpty();
    }

    public void animation(){
        TranslateAnimation ta = new TranslateAnimation(0,0,inner.getTop(),normal.top);
        ta.setDuration(200);
        inner.startAnimation(ta);
        inner.layout(normal.left,normal.top,normal.right,normal.bottom);
        normal.setEmpty();
    }

    private boolean isNeedMove(){
        int offset = inner.getMeasuredHeight() - getHeight();
        int scrollY = getScrollY();
        if (scrollY == 0 || scrollY == offset) {
            return true;
        }
        return false;

    }

    private void clear0(){
        lastX = 0;
        lastY = 0;
        distanceX = 0;
        distanceY = 0;
        upDownSlide = false;
    }
}
