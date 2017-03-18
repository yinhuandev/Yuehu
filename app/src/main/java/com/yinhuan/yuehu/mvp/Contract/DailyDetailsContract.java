package com.yinhuan.yuehu.mvp.contract;

import com.yinhuan.yuehu.mvp.presenter.BasePresenter;
import com.yinhuan.yuehu.mvp.view.BaseView;

/**
 * Created by yinhuan on 2017/3/17.
 */

public interface DailyDetailsContract {


    interface View extends BaseView{
        public void onLoadDataSuccess(Object object);

        public void onLoadDataError(Throwable e);
    }

    interface Presenter extends BasePresenter{
        public void loadDailyDetailsData(String id);
    }
}
