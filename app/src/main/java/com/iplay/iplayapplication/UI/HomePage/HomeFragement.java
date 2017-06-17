package com.iplay.iplayapplication.UI.HomePage;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.adapter.nActivityAdapter.activityAdapter.NewActivityAdapter;
import com.iplay.iplayapplication.customComponent.pullToLoad.LoadMoreDataListener;
import com.iplay.iplayapplication.entity.Activity.ActivityEntity;
import com.iplay.iplayapplication.entity.Activity.PhotoInfo;
import com.iplay.iplayapplication.entity.Activity.VideoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/5/29.
 */

public class HomeFragement extends Fragment {

    private static final String TAG = "HomeFragment";

    private SwipeRefreshLayout home_refresh;

    private List<ActivityEntity> activityEntities;

    private NewActivityAdapter adapter;

    private RecyclerView activity_view;

    private View view;

    private Handler handler = new Handler();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_fragment,container,false);
        init();
        return view;
    }

    private void init(){
        initData();
        initView();
        initListener();
    }

    private void initView(){
        activity_view = (RecyclerView) view.findViewById(R.id.activities);
        home_refresh = (SwipeRefreshLayout) view.findViewById(R.id.home_refresh);

        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        activity_view.setLayoutManager(manager);

        adapter = new NewActivityAdapter(getActivity(),activity_view);
        adapter.setData(activityEntities);
        activity_view.setAdapter(adapter);
    }

    private void initListener(){
        home_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                refresh();
            }
        });
        adapter.setLoadMoreDataListener(new LoadMoreDataListener() {
            @Override
            public void loadMoreData() {
                activityEntities.add(null);
                adapter.notifyDataSetChanged();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        activityEntities.remove(activityEntities.size()-1);
                        adapter.notifyDataSetChanged();
                        for(int i=0;i<2;i++){
                            ActivityEntity ae = new ActivityEntity();
                            PhotoInfo photoInfo = new PhotoInfo();
                            photoInfo.setPhotoId(R.drawable.wen);
                            ae.setMedia(photoInfo);
                            activityEntities.add(ae);
                        }
                        adapter.notifyDataSetChanged();
                        adapter.setLoaded();
                        Log.d("loadMore",activityEntities.size()+"");
                    }
                },5000);
            }
        });
    }

    public void refresh(){
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ActivityEntity ae1 = new ActivityEntity();
                PhotoInfo photoInfo = new PhotoInfo();
                photoInfo.setPhotoId(R.drawable.wen);
                ae1.setMedia(photoInfo);
                activityEntities.add(0,ae1);

                adapter.notifyDataSetChanged();
                home_refresh.setRefreshing(false);
            }
        },2000);
    }

    private void initData(){
        activityEntities = new ArrayList<>();

        PhotoInfo multiPi = new PhotoInfo();
        List<Integer> ids = new ArrayList<>();
        ids.add(R.drawable.square);
        ids.add(R.drawable.yinyang);
        ids.add(R.drawable.square);
        ActivityEntity ae = new ActivityEntity();
        multiPi.setPhotoIds(ids);
        ae.setMedia(multiPi);
        activityEntities.add(ae);
        VideoInfo vi = new VideoInfo();
        vi.setVideoUri("temp");
        ActivityEntity ae2 = new ActivityEntity();
        ae2.setMedia(vi);
        activityEntities.add(ae2);

        for(int i=0;i<4;i++){
            PhotoInfo photoInfo = new PhotoInfo();
            photoInfo.setPhotoId(R.drawable.shenzi);
            ActivityEntity activityEntity = new ActivityEntity();
            activityEntity.setMedia(photoInfo);
            activityEntities.add(activityEntity);
        }
        //requestToken();
        //requestActivityInfo(0);
    }
}
