package com.iplay.iplayapplication.adapter.nActivityAdapter.activityAdapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.adapter.nActivityAdapter.activityHolder.BasicViewHolder;
import com.iplay.iplayapplication.adapter.nActivityAdapter.activityHolder.MultiPhotoActivityViewHolder;
import com.iplay.iplayapplication.adapter.nActivityAdapter.activityHolder.NormalActivityViewHolder;
import com.iplay.iplayapplication.adapter.nActivityAdapter.activityHolder.ProgressBarViewHolder;
import com.iplay.iplayapplication.adapter.nActivityAdapter.activityHolder.VideoActivityViewHolder;
import com.iplay.iplayapplication.customComponent.autoChangeImageView.SquareImageView;
import com.iplay.iplayapplication.customComponent.pullToLoad.LoadMoreDataListener;
import com.iplay.iplayapplication.customComponent.pullToLoad.RecyclerOnItemClickListener;
import com.iplay.iplayapplication.entity.Activity.ActivityEntity;
import com.iplay.iplayapplication.entity.Activity.PhotoInfo;
import com.iplay.iplayapplication.entity.Activity.VideoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/12.
 */

public class NewActivityAdapter extends RecyclerView.Adapter<BasicViewHolder> {

    private static final String TAG = "adapter";

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROG = 1;
    private static final int TYPE_PHOTO_SINGLE = 2;
    private static final int TYPE_PHOTO_MULTI = 3;
    private static final int TYPE_VIDEO = 4;

    private int width;

    private final LayoutInflater inflater;

    private final Activity mContext;

    private final RecyclerView mRecyclerView;

    private List<ActivityEntity> activityEntities;

    private boolean isLoading;

    private int totalItemCount;

    private int lastVisibleItemPosition;

    private int visibleThreshold = 1;

    private LoadMoreDataListener loadMoreDataListener;

    private RecyclerOnItemClickListener recyclerOnItemClickListener;

    public NewActivityAdapter(Activity context, RecyclerView recyclerView) {
        mContext = context;
        inflater = LayoutInflater.from(mContext);
        mRecyclerView = recyclerView;
        if(mRecyclerView.getLayoutManager() instanceof LinearLayoutManager){
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) mRecyclerView.getLayoutManager();
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                    if(!isLoading && totalItemCount <= lastVisibleItemPosition + visibleThreshold){
                        if(loadMoreDataListener!=null){
                            loadMoreDataListener.loadMoreData();
                        }
                        isLoading = true;
                    }
                }
            });
        }
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        width = dm.widthPixels;
    }

    public void setLoaded(){
        isLoading = false;
    }

    public void setData(List<ActivityEntity> activityEntities){
        this.activityEntities = activityEntities;
    }

    public void setRecyclerOnItemClickListener(RecyclerOnItemClickListener listener){
        recyclerOnItemClickListener = listener;
    }

    public void setLoadMoreDataListener(LoadMoreDataListener listener){
        loadMoreDataListener = listener;
    }

    @Override
    public BasicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BasicViewHolder viewHolder = null;
        if(viewType == TYPE_PHOTO_SINGLE){
            viewHolder = new NormalActivityViewHolder(inflater.inflate(R.layout.new_activity_layout,parent,false));
        }else if(viewType == TYPE_PROG){
            viewHolder = new ProgressBarViewHolder(inflater.inflate(R.layout.activity_foot,parent,false));
        }else if(viewType == TYPE_PHOTO_MULTI){
            Log.d(TAG,"type Multi");
            viewHolder = new MultiPhotoActivityViewHolder(inflater.inflate(R.layout.multi_photos_layout,parent,false));
        }else if(viewType == TYPE_VIDEO){
            viewHolder = new VideoActivityViewHolder(inflater.inflate(R.layout.video_activity_layout,parent,false));
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BasicViewHolder holder, int position) {
        if(holder instanceof ProgressBarViewHolder){
            ProgressBarViewHolder ph = (ProgressBarViewHolder) holder;
            if(ph.progressBar!=null){
                ph.progressBar.setIndeterminate(true);
            }
        }else if(holder instanceof NormalActivityViewHolder){
            NormalActivityViewHolder nah = (NormalActivityViewHolder) holder;

            ImageView main_photo = nah.photoImage;

            ActivityEntity ae = activityEntities.get(position);

            if(ae.getMedia() instanceof PhotoInfo){
                PhotoInfo pi = (PhotoInfo) ae.getMedia();
                if(pi.getPhotoId()!=0){
                    main_photo.setImageResource(pi.getPhotoId());
                }
            }
            int screenWidth = width;
            ViewGroup.LayoutParams lp = main_photo.getLayoutParams();
            lp.width = screenWidth;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            main_photo.setLayoutParams(lp);
            main_photo.setMaxWidth(screenWidth);
            main_photo.setMaxHeight(3*screenWidth);
        }else if(holder instanceof MultiPhotoActivityViewHolder){
            MultiPhotoActivityViewHolder mh = (MultiPhotoActivityViewHolder) holder;

            ViewPager vp = mh.viewPager;

            ActivityEntity ae = activityEntities.get(position);

            if(ae.getMedia() instanceof PhotoInfo){
                PhotoInfo pi = (PhotoInfo) ae.getMedia();
                if(pi.getPhotoIds()!=null && pi.getPhotoIds().size()>0){
                    final List<Integer> ids = pi.getPhotoIds();

                    final List<SquareImageView> imageViews = new ArrayList<>();

                    for(int resId:ids){
                        SquareImageView si = new SquareImageView(mContext);
                        si.setScaleType(ImageView.ScaleType.CENTER_CROP);
                        si.setImageResource(resId);
                        imageViews.add(si);
                    }

                    vp.setAdapter(new PagerAdapter() {

                        @Override
                        public Object instantiateItem(ViewGroup container, int position) {
                            container.addView(imageViews.get(position));
                            return imageViews.get(position);
                        }

                        @Override
                        public void destroyItem(ViewGroup container, int position, Object object) {
                            container.removeView(imageViews.get(position));
                        }

                        @Override
                        public int getCount() {
                            return ids.size();
                        }

                        @Override
                        public boolean isViewFromObject(View view, Object object) {
                            return view == object;
                        }
                    });
                }
            };
        }else if(holder instanceof VideoActivityViewHolder){
            VideoActivityViewHolder vh = (VideoActivityViewHolder) holder;
            ImageView firstFrame = vh.firstFrame;
            int screenWidth = width;
            ViewGroup.LayoutParams lp = firstFrame.getLayoutParams();
            lp.width = screenWidth;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            firstFrame.setLayoutParams(lp);
            firstFrame.setMaxWidth(screenWidth);
            firstFrame.setMaxHeight(3*screenWidth);
        }
    }

    @Override
    public int getItemCount() {
        return activityEntities==null?0:activityEntities.size();
    }

    @Override
    public int getItemViewType(int position) {
        int type = TYPE_ITEM;
        if(activityEntities.get(position) == null){
            type = TYPE_PROG;
        }else{
            if(activityEntities.get(position).getMedia() instanceof PhotoInfo){
                PhotoInfo pi = (PhotoInfo) activityEntities.get(position).getMedia();
                if(pi.getPhotoId()!= 0){
                    type = TYPE_PHOTO_SINGLE;
                }
                if(pi.getPhotoIds()!=null && pi.getPhotoIds().size() > 0){
                    type = TYPE_PHOTO_MULTI;
                }
            }else if(activityEntities.get(position).getMedia() instanceof VideoInfo){
                type = TYPE_VIDEO;
            }
        }
        Log.d(TAG,"getType " + type);
        return type;
    }
}
