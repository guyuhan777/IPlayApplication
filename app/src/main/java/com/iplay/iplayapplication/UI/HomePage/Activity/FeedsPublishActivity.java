package com.iplay.iplayapplication.UI.HomePage.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.UI.HomePage.Activity.activityMediaFragment.FeedsSinglePhotoFragment;
import com.iplay.iplayapplication.UI.HomePage.Activity.activityMediaFragment.FeedsSingleVideoFragment;
import com.iplay.iplayapplication.mActivity.MyActivity;
import com.iplay.iplayapplication.message.MediaTypeMessage;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by admin on 2017/6/21.
 */

public class FeedsPublishActivity extends MyActivity implements View.OnClickListener {

    private static final String TAG = "FeedsPublishActivity";

    private CircleImageView publish_avatar;

    private static final String MEDIA_TYPE_KEY = "media_type_info";

    private FeedsSinglePhotoFragment singlePhotoFragment;

    private FeedsSingleVideoFragment singleVideoFragment;

    private TextView back_text;

    private TextView share_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_layout);
        publish_avatar = (CircleImageView) findViewById(R.id.activity_publish_avatar);
        Glide.with(this).load(R.drawable.shenzi).into(publish_avatar);

        back_text = (TextView) findViewById(R.id.activity_publish_cancel);
        back_text.setOnClickListener(this);

        share_button = (TextView) findViewById(R.id.activity_publish_share);
        share_button.setOnClickListener(this);

        initMediaContent();
    }

    private void initMediaContent(){
        MediaTypeMessage message = (MediaTypeMessage) getIntent().getSerializableExtra(MEDIA_TYPE_KEY);
        int type = message.getType();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(type == MediaTypeMessage.TYPE_SINGLE_PHOTO){
            if(singlePhotoFragment == null){
                singlePhotoFragment = new FeedsSinglePhotoFragment();
                String fileName = message.getFileName();
                Bundle args = new Bundle();
                args.putString("FILE_NAME",fileName);
                singlePhotoFragment.setArguments(args);
                transaction.replace(R.id.feeds_media_container,singlePhotoFragment);
            }
        }else if(type == MediaTypeMessage.TYPE_VIDEO){
            if(singleVideoFragment == null){
                singleVideoFragment = new FeedsSingleVideoFragment();
                String fileName = message.getFileName();
                Bundle args = new Bundle();
                args.putString("FILE_NAME",fileName);
                singleVideoFragment.setArguments(args);
                transaction.replace(R.id.feeds_media_container,singleVideoFragment);
            }
        }
        transaction.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_publish_cancel:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                });
                break;
            case R.id.activity_publish_share:
                /*BitmapHolder.remove(ImgUtils.BITMAP_KEY);
                Log.d(TAG,"remove success");*/
                break;
        }
    }

    public static void start(Context context, MediaTypeMessage message){
        Intent intent = new Intent(context,FeedsPublishActivity.class);
        intent.putExtra(MEDIA_TYPE_KEY,message);
        context.startActivity(intent);
    }
}
