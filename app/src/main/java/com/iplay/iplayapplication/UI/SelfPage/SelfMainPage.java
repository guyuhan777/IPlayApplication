package com.iplay.iplayapplication.UI.SelfPage;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.UI.HomePage.HomeFragement;
import com.iplay.iplayapplication.UI.Media.commonComponent.MultiPhotoFilterActivity;
import com.iplay.iplayapplication.mActivity.MyActivity;

import java.util.List;

import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;


/**
 * Created by admin on 2017/5/25.
 */

public class SelfMainPage extends MyActivity implements BottomNavigationBar.OnTabSelectedListener{

    private BottomNavigationBar bottom_bar;

    private SelfInfoFragment selfInfoFragment;

    private HomeFragement homeFragement;

    private static final String TAG = "SelfMainPage";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.self_info_layout);

        bottom_bar = (BottomNavigationBar) findViewById(R.id.self_info_bottom_navigation_bar);
        bottom_bar.setMode(BottomNavigationBar.MODE_FIXED);
        bottom_bar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottom_bar.setActiveColor(R.color.black);
        bottom_bar.addItem(new BottomNavigationItem(R.drawable.setting,"主页").setActiveColorResource(R.color.black)).
                addItem(new BottomNavigationItem(R.drawable.setting,"设置")).
                addItem(new BottomNavigationItem(R.drawable.setting,"设置")).
                addItem(new BottomNavigationItem(R.drawable.setting,"设置")).
                addItem(new BottomNavigationItem(R.drawable.setting,"我")).setFirstSelectedPosition(0).initialise();
        bottom_bar.setTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(int position) {
        Log.d("fragment","change");
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        switch (position){
            case 0:
                if(homeFragement == null){
                    homeFragement = new HomeFragement();
                }
                transaction.replace(R.id.fill_content,homeFragement);
                break;
            case 4:
                if(selfInfoFragment == null){
                    selfInfoFragment = new SelfInfoFragment();
                }
                transaction.replace(R.id.fill_content,selfInfoFragment);
                break;
        }
        transaction.commit();
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK &&
                (requestCode == PhotoPicker.REQUEST_CODE || requestCode == PhotoPreview.REQUEST_CODE)){
            List<String> photos = null;
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
            }
            for(int i = 0;i<photos.size();i++){
                Log.d(TAG,"photo_url:" + photos.get(i));
            }
            MultiPhotoFilterActivity.start(this,photos);
        }
    }
}
