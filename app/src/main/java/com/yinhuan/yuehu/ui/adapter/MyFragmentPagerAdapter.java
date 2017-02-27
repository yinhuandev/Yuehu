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

public class MyFragmentPagerAdapter<T extends Fragment> extends FragmentPagerAdapter {

    private List<String> mTitleList;

    private DailyFragment dailyFragment;
    private GankFragment androidFragment;
    private GankFragment iOSFragment;
    private GankFragment webFragment;
    private GankFragment resourceFragment;

    public MyFragmentPagerAdapter(FragmentManager fm, List<String> mTitleList) {
        super(fm);
        this.mTitleList = mTitleList;
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
        return mTitleList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (mTitleList != null){
            return mTitleList.get(position);
        }else {
            return "";
        }
    }


}
