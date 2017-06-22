package com.iplay.iplayapplication.UI.Media;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.UI.HomePage.Activity.FeedsPublishActivity;
import com.iplay.iplayapplication.assistance.BitmapHolder;
import com.iplay.iplayapplication.assistance.ImgUtils;
import com.iplay.iplayapplication.assistance.filter.PhotoFilterAdapter;
import com.iplay.iplayapplication.assistance.filter.PhotoFilterCallBack;
import com.iplay.iplayapplication.assistance.filter.PhotoFilterItem;
import com.iplay.iplayapplication.assistance.filter.PhotoFilterManager;
import com.iplay.iplayapplication.customComponent.autoChangeImageView.TouchImageView;
import com.iplay.iplayapplication.mActivity.MyActivity;
import com.iplay.iplayapplication.message.MediaTypeMessage;
import com.iplay.iplayapplication.util.Msg;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by admin on 2017/6/13.
 */

public class PhotoEditActivity extends MyActivity implements View.OnClickListener,PhotoFilterCallBack {

    private static final String TAG = "PhotoEditActivity";

    private TouchImageView crop_img;

    private static final String PHOTO_KEY = "PHOTO_BYTE";

    private int width;

    private TextView edit_button;

    private RecyclerView photo_filter_recycler_view;

    private Bitmap tempBitmap;

    private MyHandler myHandler;

    private Thread filterThreaad;

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crop_layout);

        edit_button = (TextView) findViewById(R.id.photo_edit_next);
        edit_button.setOnClickListener(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;

        crop_img = (TouchImageView) findViewById(R.id.crop_img);

        crop_img.setImageBitmap(BitmapHolder.get(ImgUtils.BITMAP_KEY));
        ViewGroup.LayoutParams lp = crop_img.getLayoutParams();
        lp.width = width;
        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        crop_img.setLayoutParams(lp);
        crop_img.setMaxWidth(width);
        crop_img.setMaxHeight(3*width);

        photo_filter_recycler_view = (RecyclerView) findViewById(R.id.photo_philter_recycler_view);
        initHorizontalList();
        myHandler = new MyHandler(this);
    }

    private void initHorizontalList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        photo_filter_recycler_view.setLayoutManager(layoutManager);
        photo_filter_recycler_view.setHasFixedSize(true);
        bindDataToAdapter();
    }


    private void bindDataToAdapter() {
        final Context context = this.getApplication();
        Handler handler = new Handler();
        Runnable r = new Runnable(){
            @Override
            public void run() {
                Bitmap origin = BitmapHolder.get(ImgUtils.BITMAP_KEY);
                Bitmap filterBitmap = Bitmap.createBitmap(origin,0,0,origin.getWidth(),origin.getHeight());
                PhotoFilterItem t1 = new PhotoFilterItem();
                PhotoFilterItem t2 = new PhotoFilterItem();
                //PhotoFilterItem t3 = new PhotoFilterItem();
                PhotoFilterItem t4 = new PhotoFilterItem();
                PhotoFilterItem t5 = new PhotoFilterItem();
                PhotoFilterItem t6 = new PhotoFilterItem();

                t1.image = filterBitmap;
                t2.image = filterBitmap;
                //t3.image = filterBitmap;
                t4.image = filterBitmap;
                t5.image = filterBitmap;
                t6.image = filterBitmap;

                PhotoFilterManager.clearThumbs();
                PhotoFilterManager.addThumb(t1); // Original Image

                t2.filter = SampleFilters.getStarLitFilter();
                PhotoFilterManager.addThumb(t2);

                /*t3.filter = SampleFilters.getBlueMessFilter();
                PhotoFilterManager.addThumb(t3);*/

                t4.filter = SampleFilters.getAweStruckVibeFilter();
                PhotoFilterManager.addThumb(t4);

                t5.filter = SampleFilters.getLimeStutterFilter();
                PhotoFilterManager.addThumb(t5);

                t6.filter = SampleFilters.getNightWhisperFilter();
                PhotoFilterManager.addThumb(t6);

                List<PhotoFilterItem> thumbs = PhotoFilterManager.processThumbs(context);

                PhotoFilterAdapter adapter = new PhotoFilterAdapter(thumbs, PhotoEditActivity.this);
                photo_filter_recycler_view.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        handler.post(r);
    }

    public static void start(Context context,String fileName){
        Intent intent = new Intent(context,PhotoEditActivity.class);
        intent.putExtra(PHOTO_KEY,fileName);
        context.startActivity(intent);
    }

    @Override
    public void onPhotoFilterCallback(final Filter filter) {
        filterThreaad = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap origin = BitmapHolder.get(ImgUtils.BITMAP_KEY);
                tempBitmap = Bitmap.createBitmap(origin,0,0,origin.getWidth(),origin.getHeight());
                tempBitmap = filter.processFilter(tempBitmap);
                myHandler.obtainMessage(0).sendToTarget();
            }
        });
        filterThreaad.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.photo_edit_next:
                Msg<String> msg = ImgUtils.saveEditImage(this,crop_img);
                if(msg.getMSG_TYPE() == Msg.MSG_TYPE_SUCCESS){
                    MediaTypeMessage message = new MediaTypeMessage();
                    message.setFileName(msg.getMsg());
                    message.setType(MediaTypeMessage.TYPE_SINGLE_PHOTO);
                    FeedsPublishActivity.start(PhotoEditActivity.this,message);
                    Log.d(TAG,"Edit Success" + msg.getMSG_TYPE());
                }else if(msg.getMSG_TYPE() == Msg.MSG_TYPE_FAILURE){
                    Toast.makeText(this,"Edit Failure", Toast.LENGTH_SHORT).show();
                    Log.d(TAG,"Edit Failed" + msg.getMSG_TYPE());
                }
                break;
            default:
                break;
        }
    }

    private  class MyHandler extends Handler{
        private WeakReference<PhotoEditActivity> mReference;

        private PhotoEditActivity activity;

        public MyHandler(PhotoEditActivity Pactivity){
            mReference = new WeakReference<>(Pactivity);
            activity = mReference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    activity.crop_img.setImageBitmap(activity.tempBitmap);
                    int screenWidth = activity.width;
                    ViewGroup.LayoutParams lp = activity.crop_img.getLayoutParams();
                    lp.width = screenWidth;
                    lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                    activity.crop_img.setLayoutParams(lp);
                    activity.crop_img.setMaxWidth(screenWidth);
                    activity.crop_img.setMaxHeight(3*screenWidth);
                    break;
            }
        }
    }
}
