package com.yinhuan.yuehu.http.rx;

import java.io.Serializable;

/**
 * Created by yinhuan on 2017/2/3.
 */

public class ThemeEvent implements Serializable {
    private int code;
    private Object object;

    public ThemeEvent(int code, Object object){
        this.code = code;
        this.object = object;
    }

    public int getCode() {
        return code;
    }

    public Object getObject() {
        return object;
    }
}
