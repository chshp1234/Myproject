package com.csp.myprojec.modular.comment.model;

import com.csp.myprojec.bean.CommentBean;
import com.csp.myprojec.custom.RetrofitFactory;
import com.csp.myprojec.modular.comment.contract.CommentContract;

import rx.Observable;

/**
 * Created by CSP on 2017/04/02
 */

public class CommentModelImpl implements CommentContract.Model {

    @Override
    public Observable<CommentBean> getComment(int nid, int parentid, int page) {
        return RetrofitFactory.getInstance().getComment("getcomment",
                nid, parentid, page);
    }
}