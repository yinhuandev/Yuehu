package com.yinhuan.yuehu.http;


import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yinhuan on 2017/1/28.
 */

public class HttpUtil {

    private static HttpUtil httpUtil;

    //Gank
    private final static String API_GANK_IO = "http://gank.io/api/";

    //Zhihu Daily
    public  final String API_ZHIHU_DAILY = "http://news-at.zhihu.com/api/4/news/";

    private static final int DEFAULT_TIMEOUT = 5;

    private static RetrofitHttpClient mGankClient;
    private static RetrofitHttpClient mDailyClient;

    /**
     * 封闭构造函数
     */
    private HttpUtil(){}
    /**
     * 单例实现
     * @return
     */
    public static HttpUtil getInstance(){
        if (httpUtil == null){
            httpUtil = new HttpUtil();
        }
        return httpUtil;
    }

    /**
     * 创建接口对象
     * Gank
     * @return
     */
    public RetrofitHttpClient getGankServer(){
        if (mGankClient == null){
            //手动创建一个OkHttpClient并设置超时时间
            OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
            httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_GANK_IO)
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mGankClient = retrofit.create(RetrofitHttpClient.class);
        }
        return mGankClient;
    }

    /**
     * 创建接口对象
     * Zhihu Daily
     * @return
     */
    public RetrofitHttpClient getDailyServer(){
        if (mDailyClient == null){
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIMEOUT,TimeUnit.SECONDS);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_ZHIHU_DAILY)
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            mDailyClient = retrofit.create(RetrofitHttpClient.class);
        }
        return mDailyClient;
    }

}
