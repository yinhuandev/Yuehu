package com.yinhuan.yuehu.http;

import rx.Subscription;

/**
 * Created by yinhuan on 2017/2/2.
 */

public interface HttpCallBack {

    /**
     * 处理请求成功
     * @param object
     */
    void onSuccess(Object object);

    /**
     * 处理请求失败
     */
    void onError(Throwable e);

    void addSubscription(Subscription subscription);
}
