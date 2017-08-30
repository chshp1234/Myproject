package com.csp.myprojec.custom;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by CSP on 2017/3/13.
 */

public class RxSchedulersHelper {
    public static <T> Observable.Transformer<T, T> io_main() {
        return tObservable -> tObservable
                //指定 subscribe() 所发生的线程，即 Observable.OnSubscribe被激活时所处的线程。或者叫做事件产生的线程。
                .subscribeOn(Schedulers.io())
                //指定 Subscriber 所运行在的线程。或者叫做事件消费的线程。
                .observeOn(AndroidSchedulers.mainThread());
    }
}
