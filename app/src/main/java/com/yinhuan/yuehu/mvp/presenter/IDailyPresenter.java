package com.yinhuan.yuehu.mvp.presenter;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by yinhuan on 2017/2/18.
 */

public interface IDailyPresenter {

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
