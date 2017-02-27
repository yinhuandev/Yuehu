package com.yinhuan.yuehu.application;

import android.app.Application;
import android.content.Context;

/**
 * Created by yinhuan on 2017/1/28.
 */

public class YuehuApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
    }

    public static Context getContext(){
        return context;
    }
}
