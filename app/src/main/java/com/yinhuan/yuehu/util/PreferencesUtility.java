package com.yinhuan.yuehu.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by yinhuan on 2017/2/19.
 */

public class PreferencesUtility {


    private static PreferencesUtility mInstance;

    private  static SharedPreferences preferences;

    private PreferencesUtility(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static PreferencesUtility getInstance(Context context){
        if (mInstance == null){
            mInstance = new PreferencesUtility(context);
        }
        return mInstance;
    }

    public void setNightMode(boolean b){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("mode",b);
        editor.commit();
    }

    public boolean isNight(){
        boolean b = preferences.getBoolean("mode",false);
        return b;
    }

}
