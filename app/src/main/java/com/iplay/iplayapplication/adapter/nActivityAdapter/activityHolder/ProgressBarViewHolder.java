package com.iplay.iplayapplication.adapter.nActivityAdapter.activityHolder;

import android.view.View;
import android.widget.ProgressBar;

import com.iplay.iplayapplication.R;

/**
 * Created by admin on 2017/6/12.
 */

public class ProgressBarViewHolder extends BasicViewHolder {

    public final ProgressBar progressBar;

    public ProgressBarViewHolder(View itemView) {
        super(itemView);
        progressBar = (ProgressBar)itemView.findViewById(R.id.refresh_progress_bar);
    }
}
