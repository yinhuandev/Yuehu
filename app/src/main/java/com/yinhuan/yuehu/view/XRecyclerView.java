package com.yinhuan.yuehu.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.yinhuan.yuehu.util.LogUtil;


/**
 * Created by yinhuan on 2017/2/6.
 */

public class XRecyclerView extends RecyclerView {

    //订阅者
    private OnLoadListener onLoadListener;

    private WrapAdapter mWrapAdapter;
    private SparseArray<View> mHeaderViews = new SparseArray<View>();
    private SparseArray<View> mFootViews = new SparseArray<View>();

    //是否支持下拉刷新
    private boolean pullRefreshEnabled = false;

    //是否能够加载更多
    private boolean loadingMoreEnabled = true;

    private boolean isLoadingData;
    public int previousTotal;

    //是否还有更多数据
    public boolean isNoMore;

    private float mLastY = -1;
    private static final float DRAG_RATE = 1.75f;
    // 是否是额外添加FooterView
    private boolean isOther = false;

    public XRecyclerView(Context context) {
        this(context, null);
    }

    public XRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
    }

    private void initView(Context context) {
        if (pullRefreshEnabled) {
            //初始化下拉刷新控件
        }
        LogUtil.d("HRecyclerView", "initView ->");
        LoadMoreFooter footView = new LoadMoreFooter(context);
        addFootView(footView, false);
        mFootViews.get(0).setVisibility(GONE);
    }

    /**
     * 改为公有。供外添加view使用,使用标识
     * 注意：使用后不能使用 上拉刷新，否则添加无效
     * 使用时 isOther 传入 true，然后调用 noMoreLoading即可。
     */
    public void addFootView(final View view, boolean isOther) {
        mFootViews.clear();
        mFootViews.put(0, view);
        this.isOther = isOther;
    }

    /**
     * 相当于加一个空白头布局：
     * 只有一个目的：为了滚动条显示在最顶端
     * 因为默认加了刷新头布局，不处理滚动条会下移。
     * 和 setPullRefreshEnabled(false) 一块儿使用
     * 使用下拉头时，此方法不应被使用！
     */
    public void clearHeader() {
        mHeaderViews.clear();
        final float scale = getContext().getResources().getDisplayMetrics().density;
        int height = (int) (1.0f * scale + 0.5f);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
        View view = new View(getContext());
        view.setLayoutParams(params);
        mHeaderViews.put(0, view);
    }

    public void addHeaderView(View view) {
        mHeaderViews.put(mHeaderViews.size(), view);
    }

    /**
     * 加载数据完成
     */
    public void loadMoreComplete() {
        if (isLoadingData) {
            isLoadingData = false;
            View footView = mFootViews.get(0);
            if (previousTotal <= getLayoutManager().getItemCount()) {
                if (footView instanceof LoadMoreFooter) {
                    ((LoadMoreFooter) footView).setLoadState(LoadMoreFooter.STATE_COMPLETE);
                } else {
                    footView.setVisibility(View.GONE);
                }
            } else {
                if (footView instanceof LoadMoreFooter) {
                    ((LoadMoreFooter) footView).setLoadState(LoadMoreFooter.STATE_NOMORE);
                } else {
                    footView.setVisibility(View.GONE);
                }
                isNoMore = true;
            }
            previousTotal = getLayoutManager().getItemCount();
        }
    }

    /**
     * 没有更多数据
     */
    public void noMoreLoading() {
        isLoadingData = false;
        final View footView = mFootViews.get(0);
        isNoMore = true;
        if (footView instanceof LoadMoreFooter) {
            ((LoadMoreFooter) footView).setLoadState(LoadMoreFooter.STATE_NOMORE);
        } else {
            footView.setVisibility(View.GONE);
        }
        // 额外添加的footView
        if (isOther) {
            footView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setAdapter(Adapter adapter) {
        mWrapAdapter = new WrapAdapter(mHeaderViews, mFootViews, adapter);
        super.setAdapter(mWrapAdapter);
        adapter.registerAdapterDataObserver(mDataObserver);
    }

    /**
     * 监听滚动状态的改变
     *
     * @param state
     */
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);

        //当 RecyclerView 滚动停止、订阅者不空、没有正在加载数据、能够加载数据
        if (state == RecyclerView.SCROLL_STATE_IDLE && onLoadListener != null && !isLoadingData && loadingMoreEnabled) {
            LayoutManager layoutManager = getLayoutManager();
            int lastVisibleItemPosition;//最后可见的 Item 的位置 -> position
            //获取最后可见的 Item 的位置
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] into = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(into);
                lastVisibleItemPosition = findMax(into);
            } else {
                lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

            LogUtil.d("HRecyclerVi ew", "onScrollStateChanged - >" + lastVisibleItemPosition);

            LogUtil.d("HRecyclerVi ew", "onScrollStateChanged getChildCount- >" + layoutManager.getChildCount());
            LogUtil.d("HRecyclerVi ew", "onScrollStateChanged getItemCount- >" + layoutManager.getItemCount());
            LogUtil.d("HRecyclerVi ew", "onScrollStateChanged isNoMore- >" + isNoMore);
            LogUtil.d("HRecyclerVi ew", "onScrollStateChanged - >" + lastVisibleItemPosition);
            /**
             * getChildCount 获取子布局个数。比如 header、footer、item
             *
             * lastVisibleItemPosition >= layoutManager.getItemCount() - 1
             * 假设总共 8 个Item,排最后一个 item 的 position 为7,那么 7 >= 8-1 ;如果最后可见的 Item position为5(第6个数据)
             * 那么就没有滑动到最底部了 -> 5 >= 8-1 = 7
             */
            if (layoutManager.getChildCount() > 0
                    && lastVisibleItemPosition >= layoutManager.getItemCount() - 2 && layoutManager.getItemCount() > layoutManager.getChildCount() && !isNoMore) {

                View footView = mFootViews.get(0);
                isLoadingData = true;//用于 refreshComplete 方法加载完成的判断
                if (footView instanceof LoadMoreFooter) {
                    ((LoadMoreFooter) footView).setLoadState(LoadMoreFooter.STATE_LOADING);
                    LogUtil.d("HRecyclerView", "onScrollStateChanged - >setLoadState");
                } else {
                    footView.setVisibility(View.VISIBLE);
                }

                if (isNetWorkConnected(getContext())) {
                    //有网络直接加载数据
                    onLoadListener.onLoadMore();
                    LogUtil.d("HRecyclerView", "onScrollStateChanged - >onLoadMore()");
                } else {
                    //无网络延迟 1 秒后,再重试加载一次数据
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            onLoadListener.onLoadMore();
                        }
                    }, 1000);
                }
            }
        }
    }

    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    private int findMin(int[] firstPositions) {
        int min = firstPositions[0];
        for (int value : firstPositions) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public boolean isOnTop() {
        if (mHeaderViews == null || mHeaderViews.size() == 0) {
            return false;
        }

        View view = mHeaderViews.get(0);
        if (view.getParent() != null) {
            return true;
        } else {
            return false;
        }
    }

    private final RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            mWrapAdapter.notifyDataSetChanged();
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeInserted(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount);
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount, Object payload) {
            mWrapAdapter.notifyItemRangeChanged(positionStart, itemCount, payload);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            mWrapAdapter.notifyItemRangeRemoved(positionStart, itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            mWrapAdapter.notifyItemMoved(fromPosition, toPosition);
        }
    };


    public void setPullRefreshEnabled(boolean pullRefreshEnabled) {
        this.pullRefreshEnabled = false;//暂用不到
    }

    public void setLoadingMoreEnabled(boolean loadingMoreEnabled) {
        this.loadingMoreEnabled = loadingMoreEnabled;
        if (!loadingMoreEnabled) {
            if (mFootViews != null) {
                mFootViews.remove(0);
            }
        } else {
            if (mFootViews != null) {
                LoadMoreFooter footView = new LoadMoreFooter(getContext());
                addFootView(footView, false);
            }
        }
    }


    public void setLoadMoreGone() {
        if (mFootViews == null) {
            return;
        }
        View footView = mFootViews.get(0);
        if (footView != null && footView instanceof LoadMoreFooter) {
            mFootViews.remove(0);
        }
    }


    /**
     * 观察者,监听器
     */
    public interface OnLoadListener {
        void onLoadMore();
    }

    /**
     * 订阅事件
     *
     * @param listener
     */
    public void setOnLoadListener(OnLoadListener listener) {
        this.onLoadListener = listener;
    }

    /**
     * 检测网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetWorkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    /**
     * 重置
     */
    public void reset() {
        isNoMore = false;
        final View footView = mFootViews.get(0);
        if (footView instanceof LoadMoreFooter) {
            ((LoadMoreFooter) footView).reSet();
        }
    }

}
