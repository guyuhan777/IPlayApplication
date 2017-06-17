package com.iplay.iplayapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;

import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.mActivity.MyActivity;

public class CodeVerifyActivity extends MyActivity implements View.OnClickListener {

    private TextView phone_view;

    private TextView resendCode;

    private TextView back;

    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_verify);

        phone_view = (TextView) findViewById(R.id.verify_phone);
        resendCode = (TextView) findViewById(R.id.resend_verify_code);
        next = (Button) findViewById(R.id.getCode_next);
        next.setOnClickListener(this);
        setPhoneText("8615365363655");

        back = (TextView) findViewById(R.id.register_back_level2);
        back.setOnClickListener(this);

        findViewById(R.id.verify_layout).setOnClickListener(this);
    }

    public void setPhoneText(String phoneNumber){
        phone_view.setText("+"+phoneNumber);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.getCode_next:
                Intent intent_next = new Intent(CodeVerifyActivity.this, RegisterInfoActivity.class);
                startActivity(intent_next);
                break;
            case R.id.register_back_level2:
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        onBackPressed();
                    }
                });
                break;
            case R.id.verify_layout:
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
            default:
                break;
        }
    }
}
