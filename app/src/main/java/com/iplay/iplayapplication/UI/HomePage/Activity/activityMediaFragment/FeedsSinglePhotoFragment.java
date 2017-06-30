package com.iplay.iplayapplication.UI.HomePage.Activity.activityMediaFragment;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.UI.Media.FullScreenPicActivity;

/**
 * Created by admin on 2017/6/21.
 */

public class FeedsSinglePhotoFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "FeedsSinglePhotoFragment";

    private View view;

    private ImageView singlePhoto;

    private String photoFileName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.feeds_single_photo_fragment,container,false);
        singlePhoto = (ImageView) view.findViewById(R.id.feeds_single_photo);
        singlePhoto.setOnClickListener(this);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle args = getArguments();
        String fileName = args.getString("FILE_NAME");
        photoFileName = fileName;
        Bitmap bitmap = BitmapFactory.decodeFile(fileName);
        singlePhoto.setImageBitmap(bitmap);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feeds_single_photo:
                FullScreenPicActivity.start(getActivity(),photoFileName);
                break;
        }
    }
}
