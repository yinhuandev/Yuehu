package com.yinhuan.yuehu.http;


import com.yinhuan.yuehu.mvp.bean.DailyBean;
import com.yinhuan.yuehu.mvp.bean.DailyDetailsBean;
import com.yinhuan.yuehu.mvp.bean.GankBean;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by yinhuan on 2017/1/30.
 * Retrofit 网络请求客户端
 */

public interface RetrofitHttpClient {



    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型：Android、iOS、前端、拓展资源
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{type}/{per_page}/{page}")
    Observable<HttpResult<List<GankBean>>> getGankData(@Path("type") String type, @Path("per_page") int per_page, @Path("page") int page);

    /**
     * 获取最新消息
     * http://news-at.zhihu.com/api/4/news/latest
     * @return
     */
    @GET("latest")
    Observable<DailyBean> getDailyData();

    /**
     * 获取消息详情
     * http://news-at.zhihu.com/api/4/news/9202128
     * @return
     */
    @GET("{id}")
    Observable<DailyDetailsBean> getDailyDetailData(@Path("id") String id);

    /**
     * 获取过往消息
     * eg: http://news-at.zhihu.com/api/4/news/before/20170207
     */
    @GET("before/{date}")
    Observable<DailyBean> getBeforeDailyData(@Path("date") String date);
}
