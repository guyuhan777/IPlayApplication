package com.iplay.iplayapplication.UI.Media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.mActivity.MyActivity;

/**
 * Created by admin on 2017/6/15.
 */

public class VideoPlayerActivity extends MyActivity implements View.OnClickListener{

    private static final String VIDEO_KEY = "video_key";

    private VideoView videoView;

    private MediaController mediaController;

    private String fileName;

    private TextView video_back_button;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_play_activity);
        video_back_button = (TextView) findViewById(R.id.video_player_back);
        video_back_button.setOnClickListener(this);
        videoView = (VideoView) findViewById(R.id.activity_video_player);
        mediaController = new MediaController(this);
        videoView.setMediaController(mediaController);
        fileName = getIntent().getStringExtra(VIDEO_KEY);
        videoView.setVideoPath(fileName);
        videoView.start();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.video_player_back:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                });
                break;
        }
    }

    public static void start(Context context, String fileName){
        Intent intent = new Intent(context,VideoPlayerActivity.class);
        intent.putExtra(VIDEO_KEY,fileName);
        context.startActivity(intent);
    }
}
