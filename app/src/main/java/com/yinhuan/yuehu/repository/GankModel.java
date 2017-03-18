package com.yinhuan.yuehu.repository;




import com.yinhuan.yuehu.http.HttpCallBack;
import com.yinhuan.yuehu.http.HttpResult;
import com.yinhuan.yuehu.http.HttpUtil;
import com.yinhuan.yuehu.mvp.bean.GankBean;
import com.yinhuan.yuehu.util.LogUtil;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yinhuan on 2017/2/2.
 */

public class GankModel {
    public static final String TAG = "GankModel";

    private String _type;
    private int page;
    private int per_page;

    /**
     * 设置参数
     * @param type
     * @param per_page
     * @param page
     */
    public void setParams(String type, int per_page, int page){
        this._type = type;
        this.per_page = per_page;
        this.page = page;
    }

    public void getGankData(final HttpCallBack callBack){
        LogUtil.d(TAG,"getGankData：type -> "+_type);
        Subscription subscription = HttpUtil.getInstance().getGankServer().getGankData(_type, per_page, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<HttpResult<List<GankBean>>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onError(e);
                    }

                    @Override
                    public void onNext(HttpResult<List<GankBean>> listHttpResult) {
                        LogUtil.d(TAG,"onNext："+listHttpResult.getResults().toString());
                        callBack.onSuccess(listHttpResult);
                    }
                });
        callBack.addSubscription(subscription);
    }

}
