package com.yinhuan.yuehu.mvp.presenter;




import com.yinhuan.yuehu.http.HttpCallBack;
import com.yinhuan.yuehu.mvp.model.DailyModel;
import com.yinhuan.yuehu.mvp.view.IDailyDetailsView;

import rx.Subscription;

/**
 * Created by yinhuan on 2017/2/19.
 */

public class IDailyDetailsPresenterImpl implements IDailyDetailsPresenter {


    private IDailyDetailsView dailyDetailsView;
    private DailyModel model;

    public IDailyDetailsPresenterImpl(IDailyDetailsView dailyDetailsView){
        this.dailyDetailsView = dailyDetailsView;
        model = new DailyModel();
    }

    @Override
    public void loadDailyDetailsData(String id) {
        model.setParams(id);
        model.getDailyDetailsData(new HttpCallBack() {
            @Override
            public void onSuccess(Object object) {
                dailyDetailsView.onLoadDataSuccess(object);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });
    }
}
