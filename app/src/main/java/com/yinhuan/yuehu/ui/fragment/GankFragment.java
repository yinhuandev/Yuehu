package com.yinhuan.yuehu.ui.fragment;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.mvp.bean.GankBean;
import com.yinhuan.yuehu.mvp.presenter.IGankPresenter;
import com.yinhuan.yuehu.mvp.presenter.IGankPresenterImpl;
import com.yinhuan.yuehu.mvp.view.IGankView;
import com.yinhuan.yuehu.ui.adapter.GankAdapter;
import com.yinhuan.yuehu.util.LogUtil;
import com.yinhuan.yuehu.view.XRecyclerView;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yinhuan on 2017/1/28.
 * <p>
 * Android 模块
 */

public class GankFragment extends BaseFragment implements IGankView {

    //Gank Data
    private String type = "Android";

    private GankAdapter mAdapter;

    //是否初始化好
    private boolean isInit;
    //是否第一次加载
    private boolean isFirst = true;

    private IGankPresenter gankPresenter;

    public static GankFragment newInstance(String type) {
        Bundle args = new Bundle();
        args.putString("type",type);
        GankFragment fragment = new GankFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args != null){
            type = args.getString("type");
        }
        LogUtil.d(TAG,"type - >"+type);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initParams();
    }

    /**
     * 初始化参数
     */
    private void initParams() {
        gankPresenter = new IGankPresenterImpl(this,getContext(),type);

        //订阅上拉加载
        recyclerView.setOnLoadListener(new XRecyclerView.OnLoadListener() {
            @Override
            public void onLoadMore() {
                gankPresenter.onLoadMore(recyclerView);
            }
        });

        //订阅下拉刷新
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                gankPresenter.onRefresh(swipeRefresh);
            }
        });
        //init 完成,置为 True
        isInit = true;
    }

    /**
     * 只缓加载一次
     */
    @Override
    protected void lazyLoad() {
        //如果没有初始化或 Fragment 没有显示或不是第一次进入
        if (!isInit || !isVisible || !isFirst) {
            return;
        }
        //第一次进入,取出缓存或加载数据
        gankPresenter.onLazyLoad();
    }

    @Override
    protected void setNightMode() {
        gankPresenter.setNightMode();
    }


    @Override
    protected void onFailureLoad() {
        gankPresenter.loadGankData();
    }



    @Override
    public void onLoadGankDataSuccess(Object object) {
        gankPresenter.setFooterViewInVisibility();
        List<GankBean> data = (List<GankBean>) object;
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadGankDataError(Throwable e) {
        e.printStackTrace();
    }

    @Override
    public void onSetFooterViewInVisibility() {
        recyclerView.loadMoreComplete();
    }

    @Override
    public void onSetRefreshComplete() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onClearData() {
        mAdapter.clear();
    }

    @Override
    public boolean isSetAdapter() {
        return mAdapter != null ? true : false;
    }

    @Override
    public void onSetAdapter(List<GankBean> data) {
        LogUtil.d(TAG,"onSetAdapter");
        mAdapter = new GankAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
        isFirst = false;
    }


    @Override
    public void onSetNightMode() {
        TypedValue background = new TypedValue();
        TypedValue textColorDark = new TypedValue();
        TypedValue textColorLight = new TypedValue();
        Resources.Theme theme = getActivity().getTheme();
        Resources resources = getResources();
        theme.resolveAttribute(R.attr.itemBg, background, true);
        theme.resolveAttribute(R.attr.textColorDark, textColorDark, true);
        theme.resolveAttribute(R.attr.textColorLight, textColorLight, true);

        int childCount = recyclerView.getChildCount();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            ViewGroup childView = (ViewGroup) recyclerView.getChildAt(childIndex);
            LinearLayout llAndroid = (LinearLayout) childView.findViewById(R.id.ll_gank);
            llAndroid.setBackgroundResource(background.resourceId);

            TextView tvTitle = (TextView) childView.findViewById(R.id.tv_title);
            tvTitle.setBackgroundResource(background.resourceId);
            tvTitle.setTextColor(resources.getColor(textColorDark.resourceId));

            TextView tvTime = (TextView) childView.findViewById(R.id.tv_time);
            tvTime.setBackgroundResource(background.resourceId);
            tvTime.setTextColor(resources.getColor(textColorLight.resourceId));

            TextView tvSource = (TextView) childView.findViewById(R.id.tv_who);
            tvSource.setBackgroundResource(background.resourceId);
            tvSource.setTextColor(resources.getColor(textColorLight.resourceId));
        }

        Class<RecyclerView> recyclerViewClass = RecyclerView.class;
        try {
            Field declaredField = recyclerViewClass.getDeclaredField("mRecycler");
            declaredField.setAccessible(true);
            Method declaredMethod = Class.forName(RecyclerView.Recycler.class.getName()).getDeclaredMethod("clear", (Class<?>[]) new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(declaredField.get(recyclerView), new Object[0]);
            RecyclerView.RecycledViewPool recycledViewPool = recyclerView.getRecycledViewPool();
            recycledViewPool.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onShowContentView() {
        showContentView();
    }

    @Override
    public void onShowLoadErrorView() {
        showLoadErrorView();
    }

}
