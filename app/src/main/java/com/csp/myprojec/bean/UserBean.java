package com.csp.myprojec.bean;

import java.util.List;

/**
 * Created by CSP on 2017/3/23.
 */

public class UserBean {

    /**
     * data : [{"nickname":"admin","token":"0cfdd9bc-a15b-4653-bc0b-f3dbb4bdfe61","uname":"admin"}]
     * code : 200
     * msg : 登录成功
     */

    private int code;
    private String msg;
    /**
     * nickname : admin
     * token : 0cfdd9bc-a15b-4653-bc0b-f3dbb4bdfe61
     * uname : admin
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
        private String nickname;
        private String token;
        private String uname;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getUname() {
            return uname;
        }

        public void setUname(String uname) {
            this.uname = uname;
        }
    }
}
