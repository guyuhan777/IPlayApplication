package com.iplay.iplayapplication.assistance;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by admin on 2017/6/8.
 */

public class ImgUtils {

    private ImgUtils (){

    }

    public static String getStorePath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"iplay";
    }

    public static Bitmap convertViewToMap(ImageView view){
        view.setDrawingCacheEnabled(true);
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }

    public static boolean saveEditImage(Context context,ImageView view){
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
            return true;
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
        return false;
    }

    public static boolean saveImageToGallery(Context context,byte[] bmp){
        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator+"iplay";
        File appDir = new File(storePath);
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try{
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bmp);
            fos.flush();
            fos.close();
            Uri uri = Uri.fromFile(file);
            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
            return true;
        }catch (IOException e){
            e.printStackTrace();
        }
        return false;
    }

}
