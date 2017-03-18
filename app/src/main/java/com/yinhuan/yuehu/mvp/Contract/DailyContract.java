package com.yinhuan.yuehu.mvp.contract;

import android.support.v4.widget.SwipeRefreshLayout;

import com.yinhuan.yuehu.mvp.bean.DailyBean;
import com.yinhuan.yuehu.mvp.presenter.BasePresenter;
import com.yinhuan.yuehu.mvp.view.BaseView;

import java.util.List;

/**
 * Created by yinhuan on 2017/3/17.
 */

public interface DailyContract {

    interface View extends BaseView {
        public void onLoadDailyDataSuccess(Object object);

        public void onLoadDailyDataError(Throwable e);

        public boolean isSetAdapter();

        public void onSetAdapter(List<DailyBean.StoriesBean> data);

        public void onClearData();

        public void onSetRefreshComplete();

        public void onShowContentView();

        public void onShowLoadErrorView();

        public void onSetNightMode();
    }

    interface Presenter extends BasePresenter {
        /**业务逻辑*/

        public void loadDailyData();

        public void onRefresh(SwipeRefreshLayout swipeRefreshLayout);
        /**视图逻辑*/
        public void setFooterViewInVisibility();

        public void setRefreshComplete();

        public void clearData();

        public void setNightMode();

        public void showContentView();
    }
}
