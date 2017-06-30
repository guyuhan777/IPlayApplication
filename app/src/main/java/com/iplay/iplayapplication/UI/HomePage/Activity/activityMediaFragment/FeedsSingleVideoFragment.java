package com.iplay.iplayapplication.UI.HomePage.Activity.activityMediaFragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.UI.Media.VideoPlayerActivity;

import java.io.File;

/**
 * Created by admin on 2017/6/22.
 */

public class FeedsSingleVideoFragment extends Fragment implements View.OnClickListener{

    private View view;

    private ImageView feeds_video_first_frame;

    private ImageView feeds_video_start_button;

    private String videoPath;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View retView = inflater.inflate(R.layout.feeds_single_video_fragment,container,false);
        feeds_video_first_frame = (ImageView) retView.findViewById(R.id.feeds_video_first_frame);
        feeds_video_start_button = (ImageView) retView.findViewById(R.id.feeds_video_start);
        feeds_video_start_button.setOnClickListener(this);
        return retView;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        videoPath = args.getString("FILE_NAME");
        File file = new File(videoPath);
        MediaMetadataRetriever mmr = new MediaMetadataRetriever();
        if(file.exists()){
            mmr.setDataSource(file.getAbsolutePath());
            Bitmap bitmap = mmr.getFrameAtTime();
            if(bitmap!=null){
                feeds_video_first_frame.setImageBitmap(bitmap);
            }
        }
    }

    @Override
    public void onClick(View v) {
        VideoPlayerActivity.start(getActivity(),videoPath);
    }
}
