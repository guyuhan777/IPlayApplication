package com.iplay.iplayapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.UI.SelfPage.SelfMainPage;
import com.iplay.iplayapplication.adapter.AvatarItemAdapter;
import com.iplay.iplayapplication.customComponent.RadiusCornerBottomSheet;
import com.iplay.iplayapplication.entity.AvatarChoice;
import com.iplay.iplayapplication.mActivity.MyActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisterInfoActivity extends MyActivity implements View.OnClickListener{

    private CircleImageView addPhoto;

    private RadiusCornerBottomSheet avatarList;

    private AvatarItemAdapter avatarItemAdapter;

    private List<AvatarChoice> choices;

    private TextView back;

    private View bottomSheet;

    private BottomSheetBehavior behavior;

    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_info);
        addPhoto = (CircleImageView) findViewById(R.id.register_add_photo);
        Glide.with(this).load(R.drawable.add_photo).into(addPhoto);

        next = (Button) findViewById(R.id.register_info_next);
        next.setOnClickListener(this);
        bottomSheet = findViewById(R.id.register_bottom_sheet);
        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        addPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                showBottomSheet(behavior);
            }
        });

        avatarList = (RadiusCornerBottomSheet) findViewById(R.id.avatar_list);
        initChoices();
        avatarItemAdapter = new AvatarItemAdapter(choices,RegisterInfoActivity.this);
        avatarList.setAdapter(avatarItemAdapter);

        back = (TextView) findViewById(R.id.register_back_level3);
        back.setOnClickListener(this);

        findViewById(R.id.register_info_layout).setOnClickListener(this);

        initListOnClick();
    }

    private void initChoices(){
        choices = new ArrayList<>();
        choices.add(new AvatarChoice(AvatarChoice.CHOOSE_FROM_ALBUM,"从相机中选取"));
        choices.add(new AvatarChoice(AvatarChoice.TAKE_PHOTO,"拍照"));
        choices.add(new AvatarChoice(AvatarChoice.CANCEL,"取消"));
    }

    private void showBottomSheet(BottomSheetBehavior behavior) {
        if (behavior.getState() == BottomSheetBehavior.STATE_HIDDEN) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        } else {
            behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_back_level3:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                });
                break;
            case R.id.register_info_next:
                Intent intent = new Intent(RegisterInfoActivity.this, SelfMainPage.class);
                startActivity(intent);
                break;
            case R.id.register_info_layout:
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            default:
                break;
        }
    }

    private void initListOnClick(){
        avatarList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AvatarChoice choice = choices.get(position);
                int type = choice.getType();
                switch (type){
                    case AvatarChoice.CANCEL:
                        behavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                    default:
                        break;
                }
            }
        });
    }
}
