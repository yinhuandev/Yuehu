package com.yinhuan.yuehu.mvp.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by yinhuan on 2017/1/30.
 */

public class DailyBean implements Serializable {

    private String date; //日期

    //当日新闻
    private List<StoriesBean> stories;

    //轮播器内容
    private List<TopStoriesBean> top_stories;

    public static class StoriesBean implements Serializable{
        private List<String> images;
        private String type;
        private String id;
        private String ga_prefix;
        private String title;

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public static class TopStoriesBean implements Serializable{
        private List<String> images;
        private String type;
        private String id;
        private String ga_prefix;
        private String title;

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

}

/**
 * URL: http://news-at.zhihu.com/api/4/news/latest
 * 响应实例：
 * <p>
 * {
 * date: "20140523",
 * stories: [
 * {
 * title: "中国古代家具发展到今天有两个高峰，一个两宋一个明末（多图）",
 * ga_prefix: "052321",
 * images: [
 * "http://p1.zhimg.com/45/b9/45b9f057fc1957ed2c946814342c0f02.jpg"
 * ],
 * type: 0,
 * id: 3930445
 * },
 * ...
 * ],
 * top_stories: [
 * {
 * title: "商场和很多人家里，竹制家具越来越多（多图）",
 * image: "http://p2.zhimg.com/9a/15/9a1570bb9e5fa53ae9fb9269a56ee019.jpg",
 * ga_prefix: "052315",
 * type: 0,
 * id: 3930883
 * },
 * ...
 * ]
 * }
 *
 * 分析：
 * <p>
 * date : 日期
 * stories : 当日新闻
 * title : 新闻标题
 * images : 图像地址（官方 API 使用数组形式。目前暂未有使用多张图片的情形出现，曾见无 images 属性的情况，请在使用中注意 ）
 * ga_prefix : 供 Google Analytics 使用
 * type : 作用未知
 * id : url 与 share_url 中最后的数字（应为内容的 id）
 * multipic : 消息是否包含多张图片（仅出现在包含多图的新闻中）
 * top_stories : 界面顶部 ViewPager 滚动显示的显示内容（子项格式同上）（请注意区分此处的 image 属性与 stories 中的 images 属性）
 **/