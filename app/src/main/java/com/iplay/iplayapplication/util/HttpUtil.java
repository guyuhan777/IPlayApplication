package com.iplay.iplayapplication.util;

import android.util.Log;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by admin on 2017/6/14.
 */

public class HttpUtil {

    public static void sendOKHttpRequest(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        String authenHeader = Config.getHeaderValue(Config.AUTHORIZATION);
        Request request = new Request.Builder().header(Config.AUTHORIZATION,authenHeader).url(address).build();
        client.newCall(request).enqueue(callback);
    }

    public static String getLoginToken(String username,String password){
        String ret = null;
        OkHttpClient client = new OkHttpClient();
        String json = "{" + getJsonStyleString("username")+ ":" +getJsonStyleString(username)
                + "," + getJsonStyleString("password") + ":" +getJsonStyleString(password)+"}";
        Log.d("Internet",json);
        RequestBody body = RequestBody.create(Config.JSON,json);
        Request request = new Request.Builder().
                url(Config.TOKENIP).
                post(body).build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if(response.isSuccessful()){
                return "Bearer " + response.body().string();
            }else{
                Log.d("Internet","Unknown State Error");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    private static String getJsonStyleString(String input){
        return "\""+input+"\"";
    }
}
