package com.yinhuan.yuehu.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.ui.activity.BaseActivity;
import com.yinhuan.yuehu.ui.activity.ToolbarActivity;
import com.yinhuan.yuehu.util.QRCodeUtil;
import com.yinhuan.yuehu.util.Shares;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yinhuan on 2017/1/29.
 */

public class DownloadActivity extends ToolbarActivity {

    @BindView(R.id.iv_erweima)
    ImageView ivErweima;
    @BindView(R.id.tv_share)
    TextView tvShare;

    @Override
    protected int setContentViewId() {
        return R.layout.activity_download;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        String url = "https://fir.im/yuehu";
        QRCodeUtil.showThreadImage(this, url, ivErweima, R.drawable.icon);
    }

    @Override
    protected boolean canBack() {
        return true;
    }

    public static void startAct(Context mContext) {
        Intent intent = new Intent(mContext, DownloadActivity.class);
        mContext.startActivity(intent);
    }

    @OnClick(R.id.tv_share)
    public void onShareClick(){
        Shares.share(DownloadActivity.this,R.string.string_share_text);
    }

}
