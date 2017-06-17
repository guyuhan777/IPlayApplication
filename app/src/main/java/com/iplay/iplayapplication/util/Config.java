package com.iplay.iplayapplication.util;

import java.util.HashMap;

import okhttp3.MediaType;

/**
 * Created by admin on 2017/6/14.
 */

public class Config {

    private static final HashMap<String,String> headerMap;

    public static final String AUTHORIZATION = "Authorization";

    public static final String IP = "http://192.168.191.1:8081/api/user/feeds";

    public static final String TOKENIP = "http://192.168.191.1:8081/api/auth/token";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public static String MYTOKEN;

    static {
        headerMap = new HashMap<>();
        headerMap.put(AUTHORIZATION,"Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJpdmFuMyIsImlkIjo4MTc3OTI4MDcyNzUwNjk0NCwicm9sZSI6IlVTRVIiLCJleHAiOjE1MDAwMjI1ODB9.1hcImS8OjCN_G8kkY9L8aiQfDUgW-Lucxyc7yYCwrYjJDOx9iZhkNKL_USnJUMVAui9PXiPcJj3k9GemlsrBPQ");
    }

    public static String getHeaderValue(final String key){
        return headerMap.get(key);
    }

    public static void put(String key,String value){
        headerMap.put(key,value);
    }

}
