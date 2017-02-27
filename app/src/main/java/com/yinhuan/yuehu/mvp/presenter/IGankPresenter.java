package com.yinhuan.yuehu.mvp.presenter;

import android.support.v4.widget.SwipeRefreshLayout;

import com.yinhuan.yuehu.view.XRecyclerView;


/**
 * Created by yinhuan on 2017/2/18.
 */

public interface IGankPresenter {


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
