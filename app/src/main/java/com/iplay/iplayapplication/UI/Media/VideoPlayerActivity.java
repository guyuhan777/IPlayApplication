package com.iplay.iplayapplication.UI.Media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.MediaController;
import android.widget.VideoView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.mActivity.MyActivity;

/**
 * Created by admin on 2017/6/15.
 */

public class VideoPlayerActivity extends MyActivity {

    private VideoView videoView;

    private MediaController mediaController;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_activity);

        videoView = (VideoView) findViewById(R.id.activity_video_player);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
    }

    public static void start(Context context){
        Intent intent = new Intent(context,VideoPlayerActivity.class);
        context.startActivity(intent);
    }
}
