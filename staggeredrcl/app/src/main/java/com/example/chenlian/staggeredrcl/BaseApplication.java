package com.example.chenlian.staggeredrcl;

import android.app.Application;
import android.content.Context;

/**
 * Created by cl on 2018/5/3.
 */

public class BaseApplication extends Application {
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();

        mContext = getApplicationContext();

    }

    public static Context getContext() {
        return mContext;
    }

}
