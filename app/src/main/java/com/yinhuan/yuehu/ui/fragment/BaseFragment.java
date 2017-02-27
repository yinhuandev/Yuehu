package com.yinhuan.yuehu.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.http.rx.RxBus;
import com.yinhuan.yuehu.http.rx.ThemeEvent;
import com.yinhuan.yuehu.util.LogUtil;
import com.yinhuan.yuehu.view.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by yinhuan on 2017/1/28.
 */

public abstract class BaseFragment extends Fragment {

    protected final String TAG = this.getClass().getSimpleName();

    @BindView(R.id.fl_container)
    FrameLayout contentView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout swipeRefresh;
    @BindView(R.id.recycler_view)
    XRecyclerView recyclerView;

    @BindView(R.id.ll_loading)
    LinearLayout loadingView;
    @BindView(R.id.ll_load_error)
    LinearLayout loadErrorView;

    // fragment是否显示了
    protected boolean isVisible = false;


    private CompositeSubscription mCompositeSubscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.d(TAG,"onCreate：");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.d(TAG,"onCreateView：");
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.d(TAG,"onActivityCreated：");

        initRxBus();

        //点击加载失败布局
        loadErrorView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLoadingView();
                onFailureLoad();
            }
        });

        //先隐藏内容视图，等到数据加载完成在显示出来
        contentView.setVisibility(View.GONE);
    }

    /**
     * Fragment数据的缓加载.
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onInvisible() {
    }

    protected abstract void lazyLoad();

    protected void onVisible() {
        lazyLoad();
    }


    /**
     * 设置夜间模式Ui
     */
    protected abstract void setNightMode();

    /**
     * 处理加载失败后,点击重试后的操作
     */
    protected abstract void onFailureLoad();


    /**
     * 初始化 RxBus
     */
    protected void initRxBus() {
        RxBus.getInstance().toObservable().subscribe(new Action1<Object>() {
            @Override
            public void call(Object event) {
                if (event instanceof ThemeEvent){
                    setNightMode();
                    LogUtil.d(TAG,"RxBus - >yinhuan love yaoyao");
                }
            }
        });
    }



    /**
     * 加载数据完成，显示内容视图
     */
    protected void showContentView() {
        if (loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        if (loadErrorView.getVisibility() != View.GONE) {
            loadErrorView.setVisibility(View.GONE);
        }

        if (contentView.getVisibility() != View.VISIBLE) {
            contentView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 显示加载中视图
     */
    protected void showLoadingView() {
        if (loadingView.getVisibility() != View.VISIBLE) {
            loadingView.setVisibility(View.VISIBLE);
        }
        if (contentView.getVisibility() != View.GONE) {
            contentView.setVisibility(View.GONE);
        }
        if (loadErrorView.getVisibility() != View.GONE) {
            loadErrorView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示加载失败视图
     */
    protected void showLoadErrorView() {
        if (loadingView.getVisibility() != View.GONE) {
            loadingView.setVisibility(View.GONE);
        }
        if (loadErrorView.getVisibility() != View.VISIBLE) {
            loadErrorView.setVisibility(View.VISIBLE);
        }
        if (contentView.getVisibility() != View.GONE) {
            contentView.setVisibility(View.GONE);
        }
    }

    public void addSubscription(Subscription s) {
        if (this.mCompositeSubscription == null) {
            this.mCompositeSubscription = new CompositeSubscription();
        }
        this.mCompositeSubscription.add(s);
    }

    public void removeSubscription() {
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            this.mCompositeSubscription.unsubscribe();
        }
    }

}
