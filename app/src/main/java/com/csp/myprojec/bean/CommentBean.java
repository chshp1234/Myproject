package com.csp.myprojec.bean;

import java.util.List;

/**
 * Created by CSP on 2017/4/2.
 */

public class CommentBean {
    /**
     * data : [{"content":"啊啊啊啊啊啊啊","uid":1,"id":3,"title":"每日一图：神气活现的小马","count":2,"nickname":"擦擦擦","nid":2967,"parentid":0,"commenttime":"04-02 14:40","thumb":"http://image.nationalgeographic.com.cn/2017/0328/20170328035503878.jpg"},{"content":"啊啊啊啊","uid":1,"id":2,"title":"每日一图：神气活现的小马","count":0,"nickname":"擦擦擦","nid":2967,"parentid":0,"commenttime":"04-02 14:40","thumb":"http://image.nationalgeographic.com.cn/2017/0328/20170328035503878.jpg"}]
     * code : 200
     * msg : 成功
     */

    private int code;
    private String msg;
    /**
     * content : 啊啊啊啊啊啊啊
     * uid : 1
     * id : 3
     * title : 每日一图：神气活现的小马
     * count : 2
     * nickname : 擦擦擦
     * nid : 2967
     * parentid : 0
     * commenttime : 04-02 14:40
     * thumb : http://image.nationalgeographic.com.cn/2017/0328/20170328035503878.jpg
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
        private String content;
        private int uid;
        private int id;
        private String title;
        private int count;
        private String nickname;
        private int nid;
        private int parentid;
        private String commenttime;
        private String thumb;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getNid() {
            return nid;
        }

        public void setNid(int nid) {
            this.nid = nid;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getCommenttime() {
            return commenttime;
        }

        public void setCommenttime(String commenttime) {
            this.commenttime = commenttime;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }
    }
}
