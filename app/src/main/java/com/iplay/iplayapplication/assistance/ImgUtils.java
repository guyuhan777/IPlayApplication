package com.iplay.iplayapplication.assistance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.iplay.iplayapplication.util.Msg;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by admin on 2017/6/8.
 */

public class ImgUtils {

    private static final int CAMERA_FACE_FRONT = 1;
    private static final int CAMERA_FACE_BACK = 0;

    private static final String TAG = "ImgUtils";

    private ImgUtils (){

    }

    public static final String BITMAP_KEY = "PhotoKey";

    public static String getStorePath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"iplay";
    }

    public static Bitmap convertViewToMap(ImageView view){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static Msg<String> saveEditImage(Context context,ImageView view){
        Msg<String> retMsg = new Msg<>(Msg.MSG_TYPE_FAILURE);
        String storePath = getStorePath();
        File appDir = new File(storePath);
        if(!appDir.exists()){
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);

        Bitmap bitmap = convertViewToMap(view);

        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,fos);
            fos.flush();
            fos.close();
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            fos = null;
            view.setDrawingCacheEnabled(false);
            retMsg = new Msg<>(Msg.MSG_TYPE_SUCCESS);
            retMsg.setMsg(storePath+"/"+fileName);
            return retMsg;
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(fos!=null){
                 try{
                     fos.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
                fos = null;
            }
            view.setDrawingCacheEnabled(false);
        }
        return retMsg;
    }

    public static Bitmap mirroXTranslate(Bitmap bitmap){
        Matrix m = new Matrix();
        float[] matrix_value=new float[]{1f,0f,0f,0f,-1f,0f,0f,0f,1f};
        m.setValues(matrix_value);

        try{
            Bitmap ret =  Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),m,true);
            return ret;
        }catch (OutOfMemoryError e){
            Log.d(TAG,"out of memory");
        }
        return null;
    }

    public static Bitmap adJustPhotoRotation(Bitmap bmp,final int orientationDegree){
        Matrix m = new Matrix();
        m.setRotate(orientationDegree,(float) bmp.getWidth()/2,(float)bmp.getHeight()/2);
        try {
            Bitmap bm1 = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), m, true);
            return bm1;
        }catch (OutOfMemoryError e){
            Log.d(TAG,"out of memory");
        }
        return null;

    }

    public static Msg<String> saveImageToGallery(Context context, byte[] bmp,int type){

        Log.d(TAG,"start transferring >>>>> currentTime " + System.currentTimeMillis());
        Msg<String> retMsg = null;

        String storePath = getStorePath();
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        Bitmap bitmap = BitmapFactory.decodeByteArray(bmp,0,bmp.length,options);
        bitmap = adJustPhotoRotation(bitmap, 90);
        if(type == CAMERA_FACE_BACK){
            bitmap = mirroXTranslate(bitmap);
        }
        BitmapHolder.set(BITMAP_KEY,bitmap);
        Log.d(TAG,"end Transferring >>>>> currentTime " + System.currentTimeMillis());
        try{
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,50,fos);
            fos.flush();
            fos.close();
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            retMsg = new Msg<>(Msg.MSG_TYPE_SUCCESS,fileName);
            return retMsg;
        }catch (Exception e){
            e.printStackTrace();
        }
        retMsg = new Msg<>(Msg.MSG_TYPE_FAILURE);
        return retMsg;
    }

}
