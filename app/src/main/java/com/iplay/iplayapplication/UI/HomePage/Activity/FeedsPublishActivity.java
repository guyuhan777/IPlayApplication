package com.iplay.iplayapplication.UI.HomePage.Activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.UI.HomePage.Activity.activityMediaFragment.FeedSinglePhotoFragment;
import com.iplay.iplayapplication.mActivity.MyActivity;
import com.iplay.iplayapplication.message.MediaTypeMessage;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by admin on 2017/6/21.
 */

public class FeedsPublishActivity extends MyActivity {

    private CircleImageView publish_avatar;

    private static final String MEDIA_TYPE_KEY = "media_type_info";

    private FeedSinglePhotoFragment singlePhotoFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_layout);
        publish_avatar = (CircleImageView) findViewById(R.id.activity_publish_avatar);
        Glide.with(this).load(R.drawable.shenzi).into(publish_avatar);
        initMediaContent();
    }

    private void initMediaContent(){
        MediaTypeMessage message = (MediaTypeMessage) getIntent().getSerializableExtra(MEDIA_TYPE_KEY);
        int type = message.getType();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        if(type == MediaTypeMessage.TYPE_SINGLE_PHOTO){
            if(singlePhotoFragment == null){
                singlePhotoFragment = new FeedSinglePhotoFragment();
                String fileName = message.getFileName();
                Bundle args = new Bundle();
                args.putString("FILE_NAME",fileName);
                singlePhotoFragment.setArguments(args);
                transaction.replace(R.id.feeds_media_container,singlePhotoFragment);
            }
        }
        transaction.commit();
    }

    public static void start(Context context, MediaTypeMessage message){
        Intent intent = new Intent(context,FeedsPublishActivity.class);
        intent.putExtra(MEDIA_TYPE_KEY,message);
        context.startActivity(intent);
    }
}
