package com.yinhuan.yuehu.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.yinhuan.yuehu.R;
import com.yinhuan.yuehu.mvp.contract.DailyDetailsContract;
import com.yinhuan.yuehu.mvp.bean.DailyDetailsBean;
import com.yinhuan.yuehu.mvp.presenter.DailyDetailsPresenter;
import com.yinhuan.yuehu.util.HtmlUtil;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by yinhuan on 2017/2/8.
 */

public class DailyDetailsActivity extends AppCompatActivity implements DailyDetailsContract.View {

    private String daily_id;

    @BindView(R.id.img_daily_details)
    ImageView imgDailyDetails;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progress_bar)
    ProgressBar  progressBar;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private DailyDetailsContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_details);
        ButterKnife.bind(this);
        initParams();
    }

    private void initParams() {
        Intent intent = getIntent();
        daily_id = intent.getStringExtra("daily_id");
        setSupportActionBar(toolbar);
        presenter = new DailyDetailsPresenter(this);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_back);
        }

        initWebView();

        presenter.loadDailyDetailsData(daily_id);
    }


    /**
     * 初始化 WebView
     */
    @SuppressLint("SetJavaScriptEnabled")
    private void initWebView() {
        webView.setVisibility(View.INVISIBLE);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(final WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    view.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            view.setVisibility(View.VISIBLE);
                            progressBar.setVisibility(View.GONE);
                        }
                    }, 300);
                }
            }
        });
    }


    public static void start(Context context, String id) {
        Intent intent = new Intent(context, DailyDetailsActivity.class);
        intent.putExtra("daily_id", id);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadDataSuccess(Object object) {
        DailyDetailsBean bean = (DailyDetailsBean) object;
        //设置标题
        toolbarLayout.setTitle(bean.getTitle());
        Glide.with(this).load(bean.getImage()).into(imgDailyDetails);
        String htmlData = HtmlUtil.createHtmlData(bean);
        webView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public void onLoadDataError(Throwable e) {

    }
}
