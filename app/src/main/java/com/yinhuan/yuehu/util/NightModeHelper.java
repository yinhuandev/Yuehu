package com.yinhuan.yuehu.util;

import android.content.Context;
import android.text.TextUtils;

import com.yinhuan.yuehu.http.cache.ACache;


/**
 * Created by yinhuan on 2017/2/1.
 */

public class NightModeHelper {

    //The type of Mode
    public static final String DAY = "day";
    public static final String NIGHT = "night";

    private Context context;

    private ACache mACache;

    public NightModeHelper(Context context){
        this.context = context;
        mACache = ACache.get(context);
        //初始化时设置日间主题
        mACache.put("mode",NightModeHelper.DAY);
    }

    /**
     * 获取模式
     * @return
     */
    public String getMode(){
        String mode = mACache.getAsString("mode");
        if (!TextUtils.isEmpty(mode)){
            return mode;
        }
        return null;
    }

    /**
     * 设置模式
     * @param mode
     */
    public void setMode(String mode){
        mACache.remove("mode");
        mACache.put("mode",mode);
    }

    /**
     * 是否白天模式
     * @return
     */
    public boolean isNight(){
        if (mACache.getAsString("mode").equals(NightModeHelper.NIGHT)){
            return true;
        }else {
            return false;
        }
    }

}
