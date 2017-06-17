package com.iplay.iplayapplication.UI.HomePage.Remark;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.customComponent.pullToLoad.LoadMoreDataListener;
import com.iplay.iplayapplication.entity.Remark;
import com.iplay.iplayapplication.entity.User;
import com.iplay.iplayapplication.mActivity.MyActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2017/6/3.
 */

public class RemarkActivity extends MyActivity implements LoadMoreDataListener{

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_PROG = 1;

    private List<Remark> remarks;

    private RemarkAdapter remarkAdapter;

    private RecyclerView remark_recycler_view;

    private int visibleThreshold= 1;

    private int totalItemCount;

    private int lastVisibleItemPosition;

    private boolean isLoading;

    private Handler handler = new Handler();

    private EditText remark_content;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_layout);

        remark_recycler_view = (RecyclerView) findViewById(R.id.remark_recycler_view);
        initData();
        remarkAdapter = new RemarkAdapter();
        remark_recycler_view.setAdapter(remarkAdapter);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        remark_recycler_view.setLayoutManager(linearLayoutManager);
        remark_recycler_view.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                if(!isLoading && totalItemCount <= lastVisibleItemPosition + visibleThreshold){
                    loadMoreData();
                    isLoading = true;
                }
            }
        });
        remark_recycler_view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(),0);
                return false;
            }
        });
        remark_content = (EditText) findViewById(R.id.remark_cotent);
    }

    public static void start(Context starter){
        Intent intent = new Intent(starter,RemarkActivity.class);
        starter.startActivity(intent);
    }

    public void initData(){
        remarks = new ArrayList<>();
        for(int i=0;i<10;i++){
            User u = new User();
            u.setUser_avatar(R.drawable.shenzi);
            u.setUser_name("shenzi");
            Remark remark = new Remark();
            remark.setContent("comment demo");
            remark.setRemarker(u);
            remarks.add(remark);
        }
    }

    private void setLoaded(){
        isLoading = false;
    }

    @Override
    public void loadMoreData() {
        remarks.add(null);
        remarkAdapter.notifyDataSetChanged();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                remarks.remove(remarks.size()-1);
                remarkAdapter.notifyDataSetChanged();
                User user1 = new User();
                user1.setUser_avatar(R.drawable.shenzi);
                user1.setUser_name("丰聪耳 神子");
                Remark remark1 = new Remark();
                remark1.setRemarker(user1);
                remark1.setContent("comment1");
                User user2 = new User();
                user2.setUser_name("射命丸 文");
                user2.setUser_avatar(R.drawable.wen);
                Remark remark2 = new Remark();
                remark2.setRemarker(user2);
                remark2.setContent("comment2");
                remarks.add(remark1);
                remarks.add(remark2);
                remarkAdapter.notifyDataSetChanged();
                setLoaded();
            }
        },5000);

    }

    private class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.BaseViewHolder>{

        public RemarkAdapter() {
            super();
        }

        @Override
        public int getItemCount() {
            return remarks==null?0:remarks.size();
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            BaseViewHolder holder;
            if(viewType == TYPE_ITEM){
                holder = new RemarkViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.remark_item,parent,false));
            }else{
                holder = new ProgressViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.activity_foot,parent,false));
            }
            return holder;
        }

        @Override
        public void onBindViewHolder(BaseViewHolder holder, int position) {
            if(holder instanceof RemarkViewHolder){
                RemarkViewHolder rh = (RemarkViewHolder) holder;
                rh.remark_content.setText(remarks.get(position).getContent());
                Glide.with(RemarkActivity.this).load(remarks.get(position).getRemarker().getUser_avatar()).into(rh.avatar);
                rh.name.setText(remarks.get(position).getRemarker().getUser_name());
            }else if(holder instanceof ProgressViewHolder){
                if(((ProgressViewHolder) holder).pb!=null){
                    ((ProgressViewHolder) holder).pb.setIndeterminate(true);
                }
            }
        }

        @Override
        public int getItemViewType(int position) {
            return remarks.get(position)!=null?TYPE_ITEM:TYPE_PROG;
        }

        class RemarkViewHolder extends BaseViewHolder{

            final CircleImageView avatar;

            final TextView name;

            final TextView remark_content;

            public RemarkViewHolder(View itemView) {
                super(itemView);
                avatar = (CircleImageView) itemView.findViewById(R.id.remarker_avatar);
                name = (TextView) itemView.findViewById(R.id.remarker_name);
                remark_content = (TextView) itemView.findViewById(R.id.remarker_remark);
            }
        }

        class ProgressViewHolder extends BaseViewHolder {
            private final ProgressBar pb;

            public ProgressViewHolder(View itemView) {
                super(itemView);
                pb = (ProgressBar) itemView.findViewById(R.id.refresh_progress_bar);
            }
        }

        class BaseViewHolder extends RecyclerView.ViewHolder{
            public BaseViewHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
