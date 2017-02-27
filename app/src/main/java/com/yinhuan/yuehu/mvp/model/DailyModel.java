package com.yinhuan.yuehu.mvp.model;



import com.yinhuan.yuehu.http.HttpCallBack;
import com.yinhuan.yuehu.http.HttpUtil;
import com.yinhuan.yuehu.mvp.bean.DailyBean;
import com.yinhuan.yuehu.mvp.bean.DailyDetailsBean;
import com.yinhuan.yuehu.util.LogUtil;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yinhuan on 2017/2/8.
 */

public class DailyModel {

    private String id;

    public void setParams(String id){
        this.id = id;
    }

    public void getDailyData(final HttpCallBack callBack){
        Subscription subscription = HttpUtil.getInstance().getDailyServer().getDailyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DailyBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(DailyBean dailyBean) {
                        LogUtil.d("DailyModel","onNext: - >"+dailyBean.getStories());
                        callBack.onSuccess(dailyBean);
                    }
                });
        callBack.addSubscription(subscription);
    }

    public void getDailyDetailsData(final HttpCallBack callBack){
        Subscription subscription = HttpUtil.getInstance().getDailyServer().getDailyDetailData(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<DailyDetailsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onNext(DailyDetailsBean dailyDetailsBean) {
                        callBack.onSuccess(dailyDetailsBean);
                    }
                });
        callBack.addSubscription(subscription);
    }

}
