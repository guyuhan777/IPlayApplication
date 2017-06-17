package com.iplay.iplayapplication.UI;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.iplay.iplayapplication.R;
import com.iplay.iplayapplication.entity.nActivity.nActivity;
import com.iplay.iplayapplication.mActivity.MyActivity;
import com.iplay.iplayapplication.util.Config;
import com.iplay.iplayapplication.util.HttpUtil;
import com.iplay.iplayapplication.util.Utility;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoginActivity extends MyActivity implements View.OnClickListener{

    private final String TAG = "HOME";

    private ImageView titleView;

    private TextView register_text;

    private RelativeLayout layout;

    private EditText acount;

    private EditText passwd;

    //private Thread networkThread;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.login_activity);

        titleView = (ImageView) findViewById(R.id.title_view);
        Glide.with(this).load(R.drawable.sg).into(titleView);

        register_text = (TextView) findViewById(R.id.register_text);
        register_text.setOnClickListener(this);

        acount = (EditText) findViewById(R.id.login_account);
        passwd = (EditText) findViewById(R.id.login_passwd);

        findViewById(R.id.login_layout).setOnClickListener(this);

       /* networkThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String token = HttpUtil.getLoginToken("ivan3","123456");
                Log.d(TAG,"token:" + token);
            }
        });*/
        //networkThread.start();

        //requestActivityInfo(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register_text:
                Intent intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_layout:
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                break;
        }
    }

    private void requestActivityInfo(int pageIndex) {
        String url = Config.IP + "?page=" + pageIndex;
        HttpUtil.sendOKHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "request Failure");
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final List<nActivity> activities = Utility.handleActivityRespnse(responseText);
                if (activities != null) {
                    for (int i = 0; i < activities.size(); i++) {
                        nActivity activity = activities.get(i);
                        Log.d(TAG, "pos " + i + " activity " + activity.toString());
                    }
                }
            }
        });
    }
}
