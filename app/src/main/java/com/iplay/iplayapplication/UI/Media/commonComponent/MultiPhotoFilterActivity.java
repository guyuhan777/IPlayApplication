package com.iplay.iplayapplication.UI.Media.commonComponent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.assistance.ImgUtils;
import com.iplay.iplayapplication.assistance.filter.PhotoFilterAdapter;
import com.iplay.iplayapplication.assistance.filter.PhotoFilterCallBack;
import com.iplay.iplayapplication.assistance.filter.PhotoFilterItem;
import com.iplay.iplayapplication.assistance.filter.PhotoFilterManager;
import com.iplay.iplayapplication.assistance.imageHelper.ImageHelper;
import com.iplay.iplayapplication.mActivity.MyActivity;
import com.iplay.iplayapplication.util.Msg;
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/24.
 */

public class MultiPhotoFilterActivity extends MyActivity implements ViewPager.OnPageChangeListener,PhotoFilterCallBack,View.OnClickListener{

    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private static final String TAG = "MPFActivity";

    private TextView next_photo;

    private TextView last_photo;

    private ViewPager photoViewPager;

    private static final String SELECTED_PHOTO_KEY = "selected_photo_key";

    private static final int HANDLE_FILTER_REQUEST = 0;

    private static final int HANDLE_ADAPTER_GENERATE_REQUEST = 1;

    private String[] selectedPhotos;

    private int checked[];

    private Handler photoInitHandler;

    private MyHandler filterInitHandler;

    private List<MultiPhotoCropperItem> cropperItems;

    private int width;

    private int size;

    private int currentIndex = 0;

    private RecyclerView filter_recycler_view;

    private List<PhotoFilterItem> photoFilter;

    private TextView multi_filter_next_button;

    private Bitmap currentBitmap;

    private PagerAdapter photoAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multi_photo_filter_layout);

        multi_filter_next_button = (TextView)findViewById(R.id.multi_photo_edit_next);
        multi_filter_next_button.setOnClickListener(this);

        next_photo = (TextView) findViewById(R.id.next_photo);
        next_photo.setOnClickListener(this);

        last_photo = (TextView) findViewById(R.id.pre_photo);
        last_photo.setOnClickListener(this);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
        photoViewPager = (ViewPager) findViewById(R.id.multi_photo_view_pager);

        selectedPhotos = getIntent().getStringArrayExtra(SELECTED_PHOTO_KEY);
        size = selectedPhotos.length;
        checked = new int[size];


        filter_recycler_view = (RecyclerView) findViewById(R.id.multi_photo_filter_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        layoutManager.scrollToPosition(0);
        filter_recycler_view.setLayoutManager(layoutManager);
        filter_recycler_view.setHasFixedSize(true);
        initPhotos();

        filterInitHandler = new MyHandler(this);
        changeAdapter(0);
    }

    private void changeAdapter(final int position){
        Thread adapterChangeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Bitmap filterBitmap = BitmapFactory.decodeFile(selectedPhotos[position]);
                PhotoFilterItem t1 = new PhotoFilterItem();
                PhotoFilterItem t2 = new PhotoFilterItem();
                PhotoFilterItem t3 = new PhotoFilterItem();
                PhotoFilterItem t4 = new PhotoFilterItem();
                PhotoFilterItem t5 = new PhotoFilterItem();

                t1.image = filterBitmap;
                t2.image = filterBitmap;
                t3.image = filterBitmap;
                t4.image = filterBitmap;
                t5.image = filterBitmap;

                PhotoFilterManager.clearThumbs();

                PhotoFilterManager.addThumb(t1);

                t2.filter = SampleFilters.getStarLitFilter();
                PhotoFilterManager.addThumb(t2);

                t3.filter = SampleFilters.getAweStruckVibeFilter();
                PhotoFilterManager.addThumb(t3);

                t4.filter = SampleFilters.getLimeStutterFilter();
                PhotoFilterManager.addThumb(t4);

                t5.filter = SampleFilters.getNightWhisperFilter();
                PhotoFilterManager.addThumb(t5);

                photoFilter = PhotoFilterManager.processThumbs(getApplicationContext());
                filterInitHandler.obtainMessage(HANDLE_ADAPTER_GENERATE_REQUEST,new Integer(position)).sendToTarget();
            }
        });
        adapterChangeThread.start();
    }

    public void initPhotos(){
        photoInitHandler = new Handler();
        cropperItems = new ArrayList<>();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                if(selectedPhotos!=null){
                    for(int i = 0 ;i<selectedPhotos.length;i++){
                        MultiPhotoCropperItem item = new MultiPhotoCropperItem(MultiPhotoFilterActivity.this);
                        item.setImage(selectedPhotos[i],width);
                        cropperItems.add(item);
                    }
                }
                photoAdapter = new PagerAdapter() {
                    @Override
                    public Object instantiateItem(ViewGroup container, int position) {
                        container.addView(cropperItems.get(position));
                        return cropperItems.get(position);
                    }

                    @Override
                    public void destroyItem(ViewGroup container, int position, Object object) {
                        container.removeView(cropperItems.get(position));
                    }

                    @Override
                    public int getCount() {
                        return cropperItems.size();
                    }

                    @Override
                    public boolean isViewFromObject(View view, Object object) {
                        return view == object;
                    }
                };
                photoViewPager.setAdapter(photoAdapter);
                photoViewPager.setOnPageChangeListener(MultiPhotoFilterActivity.this);
                checked[0] = 1;
            }
        };
        photoInitHandler.post(r);
    }

    public static void start(Context context, List<String> selectedPhotos){
        Intent intent = new Intent(context,MultiPhotoFilterActivity.class);
        String[] photos = null;
        photos = new String[selectedPhotos.size()];
        for(int i = 0;i<selectedPhotos.size();i++){
            photos[i] = selectedPhotos.get(i);
        }
        intent.putExtra(SELECTED_PHOTO_KEY,photos);
        context.startActivity(intent);
    }

    private boolean isAllchecked(){
        boolean ret = true;
        for (int i = 0 ; i<checked.length ; i++){
            if(checked[i] != 1){
                ret = false;
            }
        }
        return ret;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }


    @Override
    public void onPageSelected(int position) {
        currentIndex = position;
        checked[position] = 1;
        if(isAllchecked()){
            multi_filter_next_button.setTextColor(getResources().getColor(R.color.blue_hint));
        }
        changeAdapter(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onPhotoFilterCallback(final Filter filter) {
        Thread filterThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String photoPath = selectedPhotos[currentIndex];
                Bitmap originBitmap = BitmapFactory.decodeFile(photoPath);
                originBitmap = ImageHelper.getScaledBitmap(originBitmap,width);
                currentBitmap = filter.processFilter(originBitmap);
                Log.d(TAG,"on Photo Filter");
                filterInitHandler.obtainMessage(HANDLE_FILTER_REQUEST).sendToTarget();
            }
        });
        filterThread.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.multi_photo_edit_next:
                if(!isAllchecked()){
                    Toast.makeText(getApplicationContext(),"You haven't edit all of the photos",Toast.LENGTH_SHORT).show();
                }else {
                    if(cropperItems != null){
                        List<RelativeLayout> cropFields = new ArrayList<>();
                        for (int i = 0 ; i < cropperItems.size() ; i ++){
                            cropFields.add(cropperItems.get(i).crop_field);
                        }
                        Msg<List<String>> msg = ImgUtils.saveEditImages(getApplicationContext(),cropFields,width);
                        if(msg.getMSG_TYPE() == Msg.MSG_TYPE_FAILURE){
                            Toast.makeText(getApplicationContext(), "Error happened when saving all the photos", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplicationContext(),"Save all photos successfully",Toast.LENGTH_SHORT).show();
                            for ( int i = 0; i<msg.getMsg().size();i++){
                                Log.d(TAG,"filepath : " + msg.getMsg().get(i));
                            }
                        }
                    }
                }
                break;
            case R.id.next_photo:
                if(currentIndex + 1 <= size){
                    photoViewPager.setCurrentItem(currentIndex + 1);
                }
                break;
            case R.id.pre_photo:
                if(currentIndex - 1 >= 0){
                    photoViewPager.setCurrentItem(currentIndex - 1);
                }
                break;
        }
    }

    private class MyHandler extends Handler{

        private WeakReference<MultiPhotoFilterActivity> mReference;

        private MultiPhotoFilterActivity mActivity;

        public MyHandler(MultiPhotoFilterActivity activity){
            mReference = new WeakReference<MultiPhotoFilterActivity>(activity);
            mActivity = mReference.get();
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case HANDLE_FILTER_REQUEST:
                    MultiPhotoCropperItem cropperItem = cropperItems.get(currentIndex);
                    Log.d(TAG,"on Bitmap receive");
                    ImageHelper.autoFitBitmap2Image(cropperItem.crop_photo,currentBitmap,width);
                    photoAdapter.notifyDataSetChanged();
                    break;
                case HANDLE_ADAPTER_GENERATE_REQUEST:
                    PhotoFilterAdapter adapter = new PhotoFilterAdapter(photoFilter,mActivity);
                    mActivity.filter_recycler_view.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    break;
            }
        }
    }
}
