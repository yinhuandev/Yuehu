package com.yinhuan.yuehu.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.util.LogUtil;


/**
 * Created by yinhuan on 2017/2/6.
 */

public class LoadMoreFooter extends LinearLayout {

    //正在加载
    public static final int STATE_LOADING = 0;
    //加载完成
    public static final int STATE_COMPLETE = 1;
    //没有更多数据
    public static final int STATE_NOMORE = 2;

    private TextView tvLoading;
    private ProgressBar pBLoading;


    public LoadMoreFooter(Context context) {
        super(context);
        initView(context);
    }


    public LoadMoreFooter(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.footer_loading, this);
        tvLoading = (TextView) view.findViewById(R.id.tv_loading);
        pBLoading = (ProgressBar) view.findViewById(R.id.pb_loading);
        LogUtil.d("LoadMoreFooter", "ivloading");
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    /**
     * 设置加载状态
     *
     * @param state
     */
    public void setLoadState(int state) {
        switch (state) {
            case STATE_LOADING:
                pBLoading.setVisibility(View.VISIBLE);
                tvLoading.setText(getContext().getText(R.string.loading));
                this.setVisibility(View.VISIBLE);
                break;
            case STATE_COMPLETE:
                tvLoading.setText(getContext().getText(R.string.load_finish));
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                tvLoading.setText(getContext().getText(R.string.no_more));
                pBLoading.setVisibility(View.GONE);
                this.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

    public void reSet() {
        this.setVisibility(GONE);
    }
}
