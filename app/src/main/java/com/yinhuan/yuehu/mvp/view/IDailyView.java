package com.yinhuan.yuehu.mvp.view;


import com.yinhuan.yuehu.mvp.bean.DailyBean;

import java.util.List;

/**
 * Created by yinhuan on 2017/2/18.
 */

public interface IDailyView {
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
