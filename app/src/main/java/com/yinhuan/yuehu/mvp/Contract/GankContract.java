package com.yinhuan.yuehu.mvp.contract;

import android.support.v4.widget.SwipeRefreshLayout;

import com.yinhuan.yuehu.mvp.bean.GankBean;
import com.yinhuan.yuehu.mvp.presenter.BasePresenter;
import com.yinhuan.yuehu.mvp.view.BaseView;
import com.yinhuan.yuehu.view.XRecyclerView;

import java.util.List;

/**
 * Created by yinhuan on 2017/3/16.
 */

public interface GankContract {

    interface View extends BaseView{

        public void onLoadGankDataSuccess(Object object);

        public void onLoadGankDataError(Throwable e);

        public void onSetFooterViewInVisibility();

        public void onSetRefreshComplete();

        public void onClearData();

        public boolean isSetAdapter();

        public void onSetAdapter(List<GankBean> data);


        public void onSetNightMode();

        public void onShowContentView();

        public void onShowLoadErrorView();

    }

    interface Presenter extends BasePresenter{

        /**业务逻辑*/

        public void loadGankData();

        public void onLoadMore(XRecyclerView recyclerView);

        public void onRefresh(SwipeRefreshLayout swipeRefreshLayout);

        public void onLazyLoad();

        /**视图逻辑*/

        public void setFooterViewInVisibility();

        public void setRefreshComplete();

        public void clearData();

        public void setNightMode();

        public void showContentView();

    }

}
