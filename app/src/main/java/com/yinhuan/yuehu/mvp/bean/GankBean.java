package com.yinhuan.yuehu.mvp.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;


import com.yinhuan.yuehu.BR;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yinhuan on 2017/1/30.
 */

public class GankBean extends BaseObservable implements Serializable {

    private String _id;
    private String createdAt;
    private String desc;
    private List<String> images;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    @Bindable
    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
        notifyPropertyChanged(BR._id);
    }

    @Bindable
    public String getCreatedAt() {
        return createdAt;
    }


    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
        notifyPropertyChanged(BR.createdAt);
    }

    @Bindable
    public String getDesc() {
        return desc;
    }


    public void setDesc(String desc) {
        this.desc = desc;
        notifyPropertyChanged(BR.desc);
    }

    @Bindable
    public List<String> getImages() {
        return images;
    }


    public void setImages(List<String> images) {
        this.images = images;
        notifyPropertyChanged(BR.images);
    }

    @Bindable
    public String getPublishedAt() {
        return publishedAt;
    }


    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
        notifyPropertyChanged(BR.publishedAt);
    }

    @Bindable
    public String getSource() {
        return source;
    }


    public void setSource(String source) {
        this.source = source;
        notifyPropertyChanged(BR.source);
    }

    @Bindable
    public String getType() {
        return type;
    }


    public void setType(String type) {
        this.type = type;
        notifyPropertyChanged(BR.type);
    }

    @Bindable
    public String getUrl() {
        return url;
    }


    public void setUrl(String url) {
        this.url = url;
        notifyPropertyChanged(BR.url);
    }

    @Bindable
    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
        notifyPropertyChanged(BR.used);
    }

    @Bindable
    public String getWho() {
        return who;
    }


    public void setWho(String who) {
        this.who = who;
        notifyPropertyChanged(BR.who);
    }

    @Override
    public String toString() {
        return "GankBean{" +
                "who='" + who + '\'' +
                ", used=" + used +
                ", url='" + url + '\'' +
                ", type='" + type + '\'' +
                ", source='" + source + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", desc='" + desc + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", _id='" + _id + '\'' +
                '}';
    }
}
