package com.yinhuan.yuehu.http;


import java.io.Serializable;

/**
 * Created by yinhuan on 2017/2/3.
 *
 * Gank 模块 包装类
 */

public class HttpResult<T> implements Serializable{
    //是否成功
    private boolean error;

    //数据集合,传入 Bean
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "HttpResult{" +
                "error=" + error +
                ", results=" + results +
                '}';
    }
}
