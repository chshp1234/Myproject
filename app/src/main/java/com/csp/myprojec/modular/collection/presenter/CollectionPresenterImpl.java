package com.csp.myprojec.modular.collection.presenter;

import com.csp.myprojec.base.BasePresenter;
import com.csp.myprojec.bean.StateBean;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.custom.RxSchedulersHelper;
import com.csp.myprojec.modular.collection.contract.CollectionContract;
import com.csp.myprojec.modular.collection.model.CollectionModelImpl;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by CSP on 2017/03/23
 */

public class CollectionPresenterImpl extends BasePresenter<CollectionContract.View, CollectionContract.Model> implements CollectionContract.Presenter {

    public CollectionPresenterImpl(CollectionContract.View view) {
        attachView(view);
        mModel = new CollectionModelImpl();
    }


    @Override
    public void loadCollection(int page) {
        Subscription subscription = mModel.getCollection(page)
                .compose(RxSchedulersHelper.io_main())
                .subscribe(new Subscriber<NewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadFailMsg("加载失败请检查网络");
                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        if (newsBean.getCode() == 404) {
                            mView.showLoadFailMsg("请登录后再查看收藏内容");
                        } else if (newsBean.getCode() == 200) {
                            mView.showCollection(newsBean.getData());
                        }

                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void deleteItem(int nid) {
        addSubscription(mModel.getState(nid)
                .compose(RxSchedulersHelper.io_main())
                .subscribe(new Subscriber<StateBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadFailMsg("取消收藏失败请检查网络");
                    }

                    @Override
                    public void onNext(StateBean stateBean) {
                        mView.showItemDelete("取消收藏成功");
                    }
                }));
    }
}