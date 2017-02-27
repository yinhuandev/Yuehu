package com.yinhuan.yuehu.mvp.view;


import com.yinhuan.yuehu.mvp.bean.GankBean;

import java.util.List;

/**
 * Created by yinhuan on 2017/2/18.
 */

public interface IGankView {
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
