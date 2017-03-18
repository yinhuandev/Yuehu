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
import com.yinhuan.yuehu.mvp.contract.DailyContract;
import com.yinhuan.yuehu.mvp.bean.DailyBean;
import com.yinhuan.yuehu.mvp.presenter.DailyPresenter;
import com.yinhuan.yuehu.ui.adapter.DailyAdapter;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by yinhuan on 2017/1/28.
 * <p>
 * 知乎日报 模块
 */

public class DailyFragment extends BaseFragment implements DailyContract.View {

    private DailyAdapter mAdapter;

    //是否准备好加载数据
    private boolean isInit;

    DailyContract.Presenter dailyPresenter;

    public static DailyFragment newInstance() {
        Bundle args = new Bundle();
        DailyFragment fragment = new DailyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initParams();
    }

    @Override
    protected void lazyLoad() {
    }

    @Override
    protected void setNightMode() {
        dailyPresenter.setNightMode();
    }

    private void initParams() {
        dailyPresenter = new DailyPresenter(this, getContext());

        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dailyPresenter.onRefresh(swipeRefresh);
            }
        });

        dailyPresenter.loadDailyData();
    }


    /**
     * 处理加载失败后,点击重试后的操作
     */
    @Override
    protected void onFailureLoad() {
        dailyPresenter.loadDailyData();
    }


    @Override
    public void onLoadDailyDataSuccess(Object object) {
        List<DailyBean.StoriesBean> data = (List<DailyBean.StoriesBean>) object;
        mAdapter.addAll(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoadDailyDataError(Throwable e) {
        showLoadErrorView();
    }

    @Override
    public boolean isSetAdapter() {
        return mAdapter != null ? true : false;
    }

    @Override
    public void onSetAdapter(List<DailyBean.StoriesBean> data) {
        mAdapter = new DailyAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClearData() {
        mAdapter.clear();
    }

    @Override
    public void onSetRefreshComplete() {
        swipeRefresh.setRefreshing(false);
    }

    @Override
    public void onShowContentView() {
        showContentView();
    }

    @Override
    public void onShowLoadErrorView() {
        showLoadErrorView();
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
        int childCount = recyclerView.getChildCount();
        for (int childIndex = 0; childIndex < childCount; childIndex++) {
            ViewGroup childView = (ViewGroup) recyclerView.getChildAt(childIndex);
            LinearLayout llDaily = (LinearLayout) childView.findViewById(R.id.ll_daily);
            llDaily.setBackgroundResource(background.resourceId);

            TextView tvTitle = (TextView) childView.findViewById(R.id.tv_title);
            tvTitle.setBackgroundResource(background.resourceId);
            tvTitle.setTextColor(resources.getColor(textColorDark.resourceId));
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

    }
