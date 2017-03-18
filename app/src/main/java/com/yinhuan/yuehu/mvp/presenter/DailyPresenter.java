package com.yinhuan.yuehu.mvp.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;


import com.yinhuan.yuehu.http.HttpCallBack;
import com.yinhuan.yuehu.http.cache.ACache;
import com.yinhuan.yuehu.mvp.contract.DailyContract;
import com.yinhuan.yuehu.mvp.bean.DailyBean;
import com.yinhuan.yuehu.repository.DailyModel;
import com.yinhuan.yuehu.util.CheckNetwork;

import rx.Subscription;


/**
 * Created by yinhuan on 2017/2/18.
 */

public class DailyPresenter implements DailyContract.Presenter {

    static final String key = "daily";

    private DailyModel dailyModel;

    private DailyContract.View dailyView;

    private Context context;

    private ACache mACache;

    public DailyPresenter(DailyContract.View dailyView, Context context){
        this.dailyView = dailyView;
        this.context = context;

        dailyModel = new DailyModel();
        mACache = ACache.get(context);
    }


    @Override
    public void loadDailyData() {
        dailyModel.getDailyData(new HttpCallBack() {
            @Override
            public void onSuccess(Object object) {
                DailyBean bean = (DailyBean) object;
                dailyView.onShowContentView();

                if (!dailyView.isSetAdapter()){
                    if (bean.getStories() != null){
                        dailyView.onSetAdapter(bean.getStories());
                        mACache.remove(key);
                        mACache.put(key,bean,3600);
                    }
                }else {
                    if (bean.getStories() != null){
                        dailyView.onLoadDailyDataSuccess(bean.getStories());
                    }
                }

            }

            @Override
            public void onError(Throwable e) {
                dailyView.onShowLoadErrorView();
                dailyView.onLoadDailyDataError(e);
            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });
    }

    @Override
    public void onRefresh(SwipeRefreshLayout swipeRefresh) {
        if (CheckNetwork.isNetworkConnected(context)) {
            dailyView.onClearData();
            loadDailyData();
        }

        swipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                dailyView.onSetRefreshComplete();
            }
        }, 2000);
    }

    @Override
    public void setFooterViewInVisibility() {

    }

    @Override
    public void setRefreshComplete() {

    }

    @Override
    public void clearData() {

    }

    @Override
    public void setNightMode() {

    }

    @Override
    public void showContentView() {

    }
}
