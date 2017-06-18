package com.iplay.iplayapplication.util;

/**
 * Created by admin on 2017/6/17.
 */

public class Msg<T> {

    public static final int MSG_TYPE_SUCCESS = 0;
    public static final int MSG_TYPE_FAILURE = 1;

    public final int MSG_TYPE ;

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }

    private T msg;

    public Msg(int type){
        MSG_TYPE = type;
    }

    public Msg(int type,T org){
        MSG_TYPE = type;
        msg = org;
    }


}
