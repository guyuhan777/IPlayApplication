package com.iplay.iplayapplication.customComponent.autoChangeImageView;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * Created by admin on 2017/6/13.
 */

public class TouchMoveImageView extends ImageView{

    private static final String TAG = "TouchMoveImageView";

    float x_down = 0;
    float y_down = 0;
    PointF start = new PointF();
    PointF mid = new PointF();

    float oldDist = 1f;
    float oldRotation = 0;

    Matrix matrix = new Matrix();
    Matrix matrix1 = new Matrix();

    Matrix matrixZoom = new Matrix();
    boolean matrixZoomCheck = false;
    float mf[] = new float[9];
    float x1 = 0,x2 = 0,x3 = 0,x4 = 0;
    float y1 = 0,y2 = 0,y3 = 0,y4 = 0;
    Matrix matrixDrag = new Matrix();
    float xMovePosi = 0;
    float yMovePosi = 0;

    Matrix savedMatrix = new Matrix();

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    int mode = NONE;

    boolean matrixCheck = false;

    int widthScreen;
    int heightScreen;

    Bitmap originBitmap;

    public TouchMoveImageView(Activity context, Bitmap resBitmap) {
        super(context);
        originBitmap = resBitmap;
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        widthScreen = dm.widthPixels;
        heightScreen = dm.heightPixels;
        matrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
    }

    public void setImageBitmap(Bitmap resBitmap){
        originBitmap = resBitmap;
        matrix = new Matrix();
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawBitmap(originBitmap,matrix,null);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                mode = DRAG;
                x_down = event.getX();
                y_down = event.getY();
                savedMatrix.set(matrix);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mode = ZOOM;
                oldDist = spacing(event);
                savedMatrix.set(matrix);
                midPoint(mid,event);
                break;
            case MotionEvent.ACTION_MOVE:
                if(mode == ZOOM){
                    Log.d(TAG,"ACTION_MOVE:ZOOM");
                    matrix1.set(savedMatrix);
                    float newDist = spacing(event);
                    float scale = newDist / oldDist;
                    matrix1.postScale(scale,scale,mid.x,mid.y);
                    matrixCheck = matrixZoomCheck();
                    if(matrixCheck == true && !matrixZoomCheck){
                        Log.d(TAG,"ACTION_MOVE: matrixZoomCheck true");
                        matrixZoomCheck = true;
                    }
                    if(!matrixZoomCheck){
                        matrix1.getValues(mf);
                        x1 = mf[0] * 0 + mf[1] * 0 + mf[2];
                        y1 = mf[3] * 0 + mf[4] * 0 + mf[5];
                        matrixZoom.set(savedMatrix);
                        matrixZoom.postScale(scale,scale,mid.x,mid.y);
                    }
                    matrix.set(matrix1);
                    invalidate();
                }else if(mode == DRAG){
                    matrixDrag.set(savedMatrix);
                    matrixDrag.postTranslate(event.getX() - x_down , event.getY() - y_down);
                    Boolean xCheck = matrixDragXCheck(matrixDrag);
                    Boolean yCheck = matrixDragYCheck(matrixDrag);

                    if(xCheck == true && yCheck == false){
                        matrix1.set(savedMatrix);
                        matrix1.postTranslate(xMovePosi,event.getY()-y_down);
                        matrix.set(matrix1);
                        invalidate();
                        yMovePosi = event.getY() - y_down;
                    }else if(xCheck == false && yCheck == true){
                        matrix1.set(savedMatrix);
                        matrix1.postTranslate(event.getX() - x_down,yMovePosi);
                        matrix.set(matrix1);
                        invalidate();
                        xMovePosi = event.getX() - x_down;
                    }else if(xCheck == false && yCheck == false){
                        matrix1.set(savedMatrix);
                        matrix1.postTranslate(event.getX() - x_down,event.getY() - y_down);
                        matrix.set(matrix1);
                        invalidate();
                        xMovePosi = event.getX() - x_down;
                        yMovePosi = event.getY() - y_down;
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                if(mode == ZOOM && matrixZoomCheck){
                    matrixZoom.setTranslate( 0 - x1 , 0 - y1);
                    matrix.set(matrixZoom);
                    invalidate();
                    matrixZoomCheck = false;
                }else if(mode == ZOOM){
                    matrix1.getValues(mf);
                    x1 = mf[0] * 0 + mf[1] * 0 + mf[2];
                    y1 = mf[3] * 0 + mf[4] * 0 + mf[5];
                    x2 = mf[0] * originBitmap.getWidth() + mf[1] * 0 + mf[2];
                    y2 = mf[3] * originBitmap.getWidth() + mf[4] * 0 + mf[5];
                    x3 = mf[0] * 0 + mf[1] * originBitmap.getHeight() + mf[2];
                    y3 = mf[3] * 0 + mf[4] * originBitmap.getHeight() + mf[5];
                    x4 = mf[0] * originBitmap.getWidth() + mf[1] * originBitmap.getHeight() + mf[2];
                    y4 = mf[3] * originBitmap.getWidth() + mf[4] * originBitmap.getHeight() + mf[5];
                    if(x1 > 0 &&  y1 > 0){
                        matrixZoom.postTranslate(0 - x1, 0 - y1);
                    }else if(x3 > 0 && y3 < widthScreen){
                        matrixZoom.postTranslate( 0 - x3, 0 - y3);
                    }else if(x1 > 0 && y1 < 0 && y3 > widthScreen){
                        matrixZoom.postTranslate( 0 - x1, 0);
                    }else if(x1 < 0 && y1 >0 && x2 > widthScreen){
                        matrixZoom.postTranslate(0, 0 - y1);
                    }else if(x2 < widthScreen && y2 > 0){
                        matrixZoom.postTranslate(widthScreen - x2, 0 - y2);
                    }else if(x2 < widthScreen && y2 < 0 && y4 > widthScreen){
                        matrixZoom.postTranslate(widthScreen - x2, 0);
                    }else if(x4 < widthScreen && y4 < widthScreen){
                        matrixZoom.postTranslate(widthScreen - x4,widthScreen - y4);
                    }else if(x4 > widthScreen && y4 < widthScreen && x3 < widthScreen){
                        matrixZoom.postTranslate(0, widthScreen - y4);
                    }
                    matrix.set(matrixZoom);
                    invalidate();
                }
                mode = NONE;
                xMovePosi = 0;
                yMovePosi = 0;
                break;
        }
        return true;
    }

    private boolean matrixZoomCheck(){
        float[] f = new float[9];
        matrix1.getValues(f);
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x2 = f[0] * originBitmap.getWidth() + f[1] * 0 + f[2];
        float y2 = f[3] * originBitmap.getWidth() + f[4] * 0 + f[5];
        float x3 = f[0] * 0 + f[1] * originBitmap.getHeight() + f[2];
        float y3 = f[3] * 0 + f[4] * originBitmap.getHeight() + f[5];
        float x4 = f[0] * originBitmap.getWidth() + f[1] * originBitmap.getHeight() + f[2];
        float y4 = f[3] * originBitmap.getWidth() + f[4] * originBitmap.getHeight() + f[5];

        double width = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        double height = Math.sqrt((x2 - x4) * (x2 - x4) + (y2 - y4) * (y2 - y4));

        double minW = Math.min(width,height);
        Log.d(TAG,"matrixZoomCheck : width - > " + width + " height - > " + height +" minW - > " + minW );
        if(minW < widthScreen / 1 || minW > widthScreen * 5){
            return true;
        }

        return false;
    }

    private boolean matrixDragXCheck(Matrix matrixD){
        float[] f = new float[9];
        matrixD.getValues(f);
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x2 = f[0] * originBitmap.getWidth() + f[1] * 0 + f[2];
        float y2 = f[3] * originBitmap.getWidth() + f[4] * 0 + f[5];
        float x3 = f[0] * 0 + f[1] * originBitmap.getHeight() + f[2];
        float y3 = f[3] * 0 + f[4] * originBitmap.getHeight() + f[5];
        float x4 = f[0] * originBitmap.getWidth() + f[1] * originBitmap.getHeight() + f[2];
        float y4 = f[3] * originBitmap.getWidth() + f[4] * originBitmap.getHeight() + f[5];
        if(x1 > 0 || x2 < widthScreen){
            return true;
        }
        return false;
    }

    private boolean matrixDragYCheck(Matrix matrixD){
        float[] f = new float[9];
        matrixD.getValues(f);
        float x1 = f[0] * 0 + f[1] * 0 + f[2];
        float y1 = f[3] * 0 + f[4] * 0 + f[5];
        float x2 = f[0] * originBitmap.getWidth() + f[1] * 0 + f[2];
        float y2 = f[3] * originBitmap.getWidth() + f[4] * 0 + f[5];
        float x3 = f[0] * 0 + f[1] * originBitmap.getHeight() + f[2];
        float y3 = f[3] * 0 + f[4] * originBitmap.getHeight() + f[5];
        float x4 = f[0] * originBitmap.getWidth() + f[1] * originBitmap.getHeight() + f[2];
        float y4 = f[3] * originBitmap.getWidth() + f[4] * originBitmap.getHeight() + f[5];
        if(y1 > 0 || y3 < widthScreen){
            return true;
        }
        return false;
    }

    private float spacing(MotionEvent event){
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x*x+y*y);
    }

    private void midPoint(PointF point,MotionEvent event){
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2,y / 2);
    }

    public Bitmap createNewPhoto(){
        Bitmap bitmap = Bitmap.createBitmap(widthScreen,widthScreen, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);// 新建画布    
        canvas.drawBitmap(originBitmap,matrix,null);// 画图片    
        canvas.save(Canvas.ALL_SAVE_FLAG);// 保存画布    
        canvas.restore();
        return bitmap;
    }

}
