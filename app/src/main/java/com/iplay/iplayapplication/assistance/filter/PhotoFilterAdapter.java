package com.iplay.iplayapplication.assistance.filter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iplay.iplayapplication.R;

import java.util.List;

/**
 * Created by admin on 2017/6/19.
 */

public class PhotoFilterAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = "PhotoFilterAdapter";
    private static int lastPosition = -1;
    private PhotoFilterCallBack photoFilterCallback;
    private List<PhotoFilterItem> dataSet;

    public PhotoFilterAdapter(List<PhotoFilterItem> dataSet, PhotoFilterCallBack thumbnailCallback) {
        Log.v(TAG, "PhotoFilterAdapter has " + dataSet.size() + " items");
        this.dataSet = dataSet;
        this.photoFilterCallback = thumbnailCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        Log.v(TAG, "On Create View Holder Called");
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.photo_filter_item, viewGroup, false);
        return new PhotoFilterViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PhotoFilterItem photoFilterItem = dataSet.get(position);
        Log.v(TAG, "On Bind View Called");
        PhotoFilterViewHolder photonailsViewHolder = (PhotoFilterViewHolder) holder;
        photonailsViewHolder.photoNail.setImageBitmap(photoFilterItem.image);
        photonailsViewHolder.photoNail.setScaleType(ImageView.ScaleType.FIT_START);
        photonailsViewHolder.photoNail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lastPosition != position) {
                    photoFilterCallback.onPhotoFilterCallback(photoFilterItem.filter);
                    lastPosition = position;
                }
            }

        });
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public static class PhotoFilterViewHolder extends RecyclerView.ViewHolder {
        public ImageView photoNail;

        public PhotoFilterViewHolder(View v) {
            super(v);
            this.photoNail = (ImageView) v.findViewById(R.id.photonail);
        }
    }
}
