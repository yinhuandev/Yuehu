package com.yinhuan.yuehu.application;

import android.app.Application;
import android.content.Context;

import com.yinhuan.yuehu.util.Toasts;

/**
 * Created by yinhuan on 2017/1/28.
 */

public class App extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        context = getApplicationContext();
        Toasts.register(this);
    }

    public static Context getContext(){
        return context;
    }
}
