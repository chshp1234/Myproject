package com.csp.myprojec.bean;

import java.util.List;

/**
 * Created by CSP on 2017/3/11.
 */

public class NewsBean {
    /**
     * data : [{"content":"http://image.nationalgeographic.com.cn/2017/0306/20170306031019458.jpg","title":"每日一图：嗨伙计，先别走！","description":"你来掌镜摄影师B Mahesh与这只色彩明亮的变色龙偶遇，彼时他正前往观鸟。","thumb":"http://image.nationalgeographic.com.cn/2017/0306/20170306031037526.jpg","inputtime":"2017.03.06"}]
     * code : 200
     * msg : 成功
     */

    private int code;
    private String msg;
    /**
     * content : http://image.nationalgeographic.com.cn/2017/0306/20170306031019458.jpg
     * title : 每日一图：嗨伙计，先别走！
     * description : 你来掌镜摄影师B Mahesh与这只色彩明亮的变色龙偶遇，彼时他正前往观鸟。
     * thumb : http://image.nationalgeographic.com.cn/2017/0306/20170306031037526.jpg
     * inputtime : 2017.03.06
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int id;
        private String content;
        private String title;
        private String description;
        private String thumb;
        private String inputtime;
        private int isCollect;

        public int getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(int isCollect) {
            this.isCollect = isCollect;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public String getInputtime() {
            return inputtime;
        }

        public void setInputtime(String inputtime) {
            this.inputtime = inputtime;
        }
    }
}
