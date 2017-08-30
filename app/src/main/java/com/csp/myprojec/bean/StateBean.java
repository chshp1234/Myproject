package com.csp.myprojec.bean;

import java.util.List;

/**
 * Created by CSP on 2017/3/23.
 */

public class StateBean {

    /**
     * data : []
     * code : 200
     * msg : 评论成功
     */

    private int code;
    private String msg;
    private List<?> data;

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

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
