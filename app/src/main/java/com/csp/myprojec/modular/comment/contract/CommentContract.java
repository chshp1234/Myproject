package com.csp.myprojec.modular.comment.contract;

import com.csp.myprojec.base.BaseView;
import com.csp.myprojec.bean.CommentBean;

import java.util.List;

import rx.Observable;

/**
 * Created by CSP on 2017/4/2.
 */

public interface CommentContract {
    interface View extends BaseView {
        void showComment(List<CommentBean.DataBean> dataBean);

        void showLoadFailMsg(String msg);
    }

    interface Presenter {
        void loadComment(int nid, int parentid, int page);
    }

    interface Model {
        Observable<CommentBean> getComment(int nid, int parentid, int page);
    }


}