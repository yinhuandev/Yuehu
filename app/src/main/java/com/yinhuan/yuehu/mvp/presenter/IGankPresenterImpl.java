package com.yinhuan.yuehu.mvp.presenter;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;


import com.yinhuan.yuehu.http.HttpCallBack;
import com.yinhuan.yuehu.http.HttpResult;
import com.yinhuan.yuehu.http.cache.ACache;
import com.yinhuan.yuehu.mvp.bean.GankBean;
import com.yinhuan.yuehu.mvp.model.GankModel;
import com.yinhuan.yuehu.mvp.view.IGankView;
import com.yinhuan.yuehu.util.CheckNetwork;
import com.yinhuan.yuehu.util.LogUtil;
import com.yinhuan.yuehu.view.XRecyclerView;

import java.util.List;

import rx.Subscription;

/**
 * Created by yinhuan on 2017/2/18.
 */

public class IGankPresenterImpl implements IGankPresenter {

    private IGankView gankView;

    private GankModel gankModel;

    private String type;
    private int per_page = 10, page = 1;

    private ACache mACache ;

    private Context context;

    public IGankPresenterImpl(IGankView gankView, Context context,String type){
        this.gankView = gankView;
        this.context = context;
        this.type = type;
        gankModel = new GankModel();
        mACache = ACache.get(context);
    }

    @Override
    public void loadGankData() {
        gankModel.setParams(type,per_page,page);
        LogUtil.d("loadGankData()","page ->"+page);
        gankModel.getGankData(new HttpCallBack() {
            @Override
            public void onSuccess(Object object) {
                HttpResult<List<GankBean>> httpResult = (HttpResult) object;
                if (!gankView.isSetAdapter()) {
                    if (httpResult != null && httpResult.getResults() != null && httpResult.getResults().size() > 0) {
                        List<GankBean> mData = httpResult.getResults();
                        gankView.onSetAdapter(mData);
                        mACache.remove(type);
                        mACache.put(type, httpResult, 86400);
                    }
                } else {
                    //加载更多
                    if (httpResult != null && httpResult.getResults() != null && httpResult.getResults().size() > 0) {
                        gankView.onLoadGankDataSuccess(httpResult.getResults());
                    } else {
                        gankView.onSetFooterViewInVisibility();
                    }
                }
            }

            @Override
            public void onError(Throwable e) {
                gankView.onShowLoadErrorView();
            }

            @Override
            public void addSubscription(Subscription subscription) {

            }
        });
    }


    @Override
    public void onLoadMore(XRecyclerView recyclerView) {
        if (CheckNetwork.isNetworkConnected(context)) {
            page++;
            loadGankData();
        } else {
            recyclerView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    gankView.onSetFooterViewInVisibility();
                }
            }, 2000);
        }
    }

    @Override
    public void onRefresh(SwipeRefreshLayout swipeRefresh) {

        if (CheckNetwork.isNetworkConnected(context)) {
            gankView.onClearData();
            page = 1;
            loadGankData();
        } else {
                    /*
                    Snackbar.make(swipeRefresh,"网络开小差喔",Snackbar.LENGTH_SHORT)
                            .setAction("知道了", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                }
                            }).show();
                            */
        }

        swipeRefresh.postDelayed(new Runnable() {
            @Override
            public void run() {
                gankView.onSetRefreshComplete();
            }
        }, 2000);
    }

    @Override
    public void onLazyLoad() {
        HttpResult<List<GankBean>> bean = (HttpResult<List<GankBean>>) mACache.getAsObject(type);
        if (bean != null) {
            gankView.onShowContentView();
            gankView.onSetAdapter(bean.getResults());
        } else {
            loadGankData();
        }
    }



    @Override
    public void setFooterViewInVisibility() {
        gankView.onSetFooterViewInVisibility();
    }

    @Override
    public void setRefreshComplete() {
        gankView.onSetRefreshComplete();
    }

    @Override
    public void clearData() {
        gankView.onClearData();
    }

    @Override
    public void setNightMode() {
        gankView.onSetNightMode();
    }

    @Override
    public void showContentView() {
        gankView.onShowContentView();
    }

}
