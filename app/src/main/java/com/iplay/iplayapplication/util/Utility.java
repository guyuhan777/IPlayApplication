package com.iplay.iplayapplication.util;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.iplay.iplayapplication.entity.nActivity.nActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/14.
 */

public class Utility {

    public static String handleTokenResponse(String respnse){
        String token = null;
        try {
            JSONObject object = new JSONObject(respnse);
            token = object.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return token;
    }

    public static List<nActivity> handleActivityRespnse(String response){

        List<nActivity> nActivities = null;

        if(!TextUtils.isEmpty(response)){
            try{
                nActivities = new ArrayList<>();
                JSONArray activities = new JSONArray(response);
                for(int i=0;i<activities.length();i++){
                    String activity_text = activities.getJSONObject(i).toString();
                    nActivity activity = new Gson().fromJson(activity_text,nActivity.class);
                    nActivities.add(activity);
                }
            }catch (JSONException e){
                e.printStackTrace();
            }
        }

        return nActivities;
    }

}
