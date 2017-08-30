package com.csp.myprojec.modular.dailyphoto.presenter;

import android.util.Log;

import com.csp.myprojec.base.BasePresenter;
import com.csp.myprojec.bean.StateBean;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.custom.RxSchedulersHelper;
import com.csp.myprojec.modular.dailyphoto.contract.DailyPhotoContract;
import com.csp.myprojec.modular.dailyphoto.model.DailyPhotoModelImpl;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by CSP on 2017/03/22
 */

public class DailyPhotoPresenterImpl extends BasePresenter<DailyPhotoContract.View, DailyPhotoContract.Model> implements DailyPhotoContract.Presenter {

    public DailyPhotoPresenterImpl(DailyPhotoContract.View view) {
        attachView(view);
        mModel = new DailyPhotoModelImpl();
    }

    @Override
    public void loadPhotoList(int page) {
        Subscription subscription = mModel.getPhotoList(page)
                .compose(RxSchedulersHelper.io_main())
                .subscribe(new Subscriber<NewsBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadFailMsg("加载失败，请检查网络");
                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        Log.i("data", "page:" + page);
                        mView.showPhotoList(newsBean.getData());
                    }
                });
        addSubscription(subscription);
    }

    @Override
    public void collectPhoto(int nid, int position) {
        Subscription subscription = mModel.getState(nid)
                .compose(RxSchedulersHelper.io_main())
                .subscribe(new Subscriber<StateBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.showLoadFailMsg("收藏失败，请检查网络");
                    }

                    @Override
                    public void onNext(StateBean collectStateBean) {
                        Log.i("code", collectStateBean.getCode() + "");
                        mView.showCollectState(collectStateBean, position);
                    }
                });
        addSubscription(subscription);
    }
}