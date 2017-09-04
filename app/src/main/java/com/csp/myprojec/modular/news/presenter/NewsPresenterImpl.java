package com.csp.myprojec.modular.news.presenter;

import android.util.Log;

import com.csp.myprojec.base.BasePresenter;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.custom.RxSchedulersHelper;
import com.csp.myprojec.modular.news.NewsListFragment;
import com.csp.myprojec.modular.news.contract.NewsContract;
import com.csp.myprojec.modular.news.model.NewsModelImpl;
import com.github.lazylibrary.util.ToastUtils;

import rx.Subscriber;
import rx.Subscription;

/**
 * Created by CSP on 2017/03/12
 */

public class NewsPresenterImpl extends BasePresenter<NewsContract.View,NewsContract.Model> implements NewsContract.Presenter {

    public NewsPresenterImpl(NewsContract.View view) {
        attachView(view);
        mModel = new NewsModelImpl();
    }

    @Override
    public void loadNewsList(int page, String category) {
        Subscription subscription = mModel.getNewsList(page, category)
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
                        Log.i("data","第"+page+"页"+category+"分类");
                        mView.showNewsList(newsBean.getData());
                    }
                });
        addSubscription(subscription);
    }
}