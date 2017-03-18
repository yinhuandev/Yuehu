package com.yinhuan.yuehu.http;


import com.google.gson.Gson;
import com.yinhuan.yuehu.mvp.bean.GankBean;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
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



    static class FileRequestBodyConverterFactory extends Converter.Factory{

        @Override
        public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
            return new FileRequestBodyConverter();
        }
    }


    static class FileRequestBodyConverter implements Converter<File, RequestBody>{

        @Override
        public RequestBody convert(File file) throws IOException {
            return RequestBody.create(MediaType.parse("application/yinhuan-stream"),file);
        }
    }

    static class ArbitraryResponseBodyConverterFactory extends Converter.Factory{

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            return super.responseBodyConverter(type, annotations, retrofit);
        }
    }

    static class ArbitraryResponseBodyConverter implements Converter<RequestBody,HttpResult>{

        @Override
        public HttpResult convert(RequestBody value) throws IOException {

            RawResult rawResult = new Gson().fromJson(value.toString(),RawResult.class);
            HttpResult result = new HttpResult();

            return null;
        }
    }



    static class RawResult{
        int err;
        String content;
        String message;
    }
}
