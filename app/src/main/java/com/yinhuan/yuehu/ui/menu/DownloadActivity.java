package com.yinhuan.yuehu.ui.menu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.ui.activity.BaseActivity;


/**
 * Created by yinhuan on 2017/1/29.
 */

public class DownloadActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        /*
        showContentView();
        setTitle(getString(R.string.download));

        String url = "https://fir.im/ukd7";
        QRCodeUtil.showThreadImage(this, url, bindingView.ivErweima, R.drawable.icon);
        bindingView.tvShare.setOnClickListener(new PerfectClickListener() {
            @Override
            protected void onNoDoubleClick(View v) {
                ShareUtils.share(v.getContext(), R.string.string_share_text);
            }
        });
        */
    }

    public static void start(Context mContext) {
        Intent intent = new Intent(mContext, DownloadActivity.class);
        mContext.startActivity(intent);
    }
}
