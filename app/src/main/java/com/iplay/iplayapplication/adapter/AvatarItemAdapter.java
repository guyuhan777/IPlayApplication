package com.iplay.iplayapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.entity.AvatarChoice;

import java.util.List;

/**
 * Created by admin on 2017/5/24.
 */

public class AvatarItemAdapter extends BaseAdapter {

    private List<AvatarChoice> datalist;

    private LayoutInflater inflater;

    public AvatarItemAdapter() {
        super();
    }

    public AvatarItemAdapter(List<AvatarChoice> datalist, Context context){
        this.datalist = datalist;
        this.inflater =LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datalist.size();
    }

    @Override
    public Object getItem(int position) {
        return datalist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = inflater.inflate(R.layout.avatar_item,parent,false);
        AvatarChoice avatarChoice = (AvatarChoice) getItem(position);
        TextView titleView = (TextView) view.findViewById(R.id.avatar_choice_title);
        titleView.setText(avatarChoice.getTitle());
        return  view;
    }
}
