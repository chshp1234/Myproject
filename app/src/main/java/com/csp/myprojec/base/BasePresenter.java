package com.csp.myprojec.base;

import android.util.Log;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V,M> {
    public V mView;
    public M mModel;
    private CompositeSubscription mCompositeSubscription;

    public void attachView(V mView){
        Log.i("attachView",mView.getClass().getName());
        this.mView = mView;
    }

    public void detachView() {
//        if (mView != null) {
//            mView = null;
//        }
        onUnsubscribe();
    }


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.clear();
            Log.i("onUnsubscribe",!mCompositeSubscription.hasSubscriptions()+"");
        }
    }


    public void addSubscription(Subscription subscription) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscription);
        Log.i("addSubscription",mCompositeSubscription.hasSubscriptions()+"");
    }
}
