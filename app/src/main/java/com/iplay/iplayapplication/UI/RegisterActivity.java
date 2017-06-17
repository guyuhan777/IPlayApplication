package com.iplay.iplayapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.customComponent.EditTextWithLabel;
import com.iplay.iplayapplication.mActivity.MyActivity;

/**
 * Created by admin on 2017/5/23.
 */

public class RegisterActivity extends MyActivity implements View.OnClickListener{

    private EditTextWithLabel zone_phone;

    private Button next;

    private TextView back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        zone_phone = (EditTextWithLabel) findViewById(R.id.zone_phone);
        zone_phone.setSubViewFocus();

        next = (Button) findViewById(R.id.register_button_next);
        next.setOnClickListener(this);

        back = (TextView) findViewById(R.id.register_back_level1);
        back.setOnClickListener(this);

        findViewById(R.id.activity_register).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_button_next:
                Intent intent_next = new Intent(RegisterActivity.this, CodeVerifyActivity.class);
                startActivity(intent_next);
                break;
            case R.id.register_back_level1:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                });
                break;
            case R.id.activity_register:
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            default:
                break;
        }
    }
}
