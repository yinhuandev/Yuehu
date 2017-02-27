package com.yinhuan.yuehu.http.rx;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * Created by yinhuan on 2017/1/28.
 */

public class RxBus {

    private static volatile RxBus mRxBus;

    private RxBus(){

    }

    public static RxBus getInstance(){
        if (mRxBus == null){
            synchronized (RxBus.class){
                if (mRxBus == null){
                    mRxBus = new RxBus();
                }
            }
        }
        return mRxBus;
    }

    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    public void send(Object object){
        _bus.onNext(object);
    }


    public Observable<Object> toObservable(){
        return _bus;
    }
}
