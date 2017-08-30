package com.csp.myprojec.modular.comment.presenter;

import android.util.Log;

import com.csp.myprojec.base.BasePresenter;
import com.csp.myprojec.bean.CommentBean;
import com.csp.myprojec.custom.RxSchedulersHelper;
import com.csp.myprojec.modular.comment.contract.CommentContract;
import com.csp.myprojec.modular.comment.model.CommentModelImpl;

import rx.Subscriber;

/**
 * Created by CSP on 2017/04/02
 */

public class CommentPresenterImpl extends BasePresenter<CommentContract.View, CommentContract.Model> implements CommentContract.Presenter {

    public CommentPresenterImpl(CommentContract.View view) {
        attachView(view);
        mModel = new CommentModelImpl();
    }

    @Override
    public void loadComment(int nid, int parentid, int page) {
        addSubscription(mModel.getComment(nid, parentid, page)
                .compose(RxSchedulersHelper.io_main())
                .subscribe(new Subscriber<CommentBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadFailMsg("获取评论失败，请检查网络");

                    }

                    @Override
                    public void onNext(CommentBean commentBean) {
                        if (commentBean.getCode()==200){
                            mView.showComment(commentBean.getData());
                        }
                    }
                }));
    }
}