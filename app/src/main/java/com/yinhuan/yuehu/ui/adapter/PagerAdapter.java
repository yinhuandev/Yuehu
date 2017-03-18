package com.yinhuan.yuehu.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


import com.yinhuan.yuehu.ui.fragment.DailyFragment;
import com.yinhuan.yuehu.ui.fragment.GankFragment;

import java.util.List;

/**
 * Created by yinhuan on 2017/1/28.
 */

public class PagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private DailyFragment dailyFragment;
    private GankFragment androidFragment;
    private GankFragment iOSFragment;
    private GankFragment webFragment;
    private GankFragment resourceFragment;

    private String[] title = {"知乎日报","Android","iOS","前端","拓展资源"};

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                if (dailyFragment == null){
                    dailyFragment = DailyFragment.newInstance();
                }
                return dailyFragment;
            case 1:
                if (androidFragment == null){
                    androidFragment = GankFragment.newInstance("Android");
                }
                return androidFragment;
            case 2:
                if (iOSFragment == null){
                    iOSFragment = GankFragment.newInstance("iOS");
                }
                return iOSFragment;
            case 3:
                if (webFragment == null){
                    webFragment = GankFragment.newInstance("前端");
                }
                return webFragment;
            case 4:
                if (resourceFragment == null){
                    resourceFragment = GankFragment.newInstance("拓展资源");
                }
                return resourceFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (title != null){
            return title[position];
        }else {
            return "";
        }
    }

}
