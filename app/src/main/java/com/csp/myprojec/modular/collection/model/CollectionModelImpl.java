package com.csp.myprojec.modular.collection.model;

import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.StateBean;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.custom.RetrofitFactory;
import com.csp.myprojec.modular.collection.contract.CollectionContract;

import rx.Observable;

/**
 * Created by CSP on 2017/03/23
 */

public class CollectionModelImpl implements CollectionContract.Model {

    @Override
    public Observable<NewsBean> getCollection(int page) {
        return RetrofitFactory.getInstance().getCollection("getcollection", page, MyApplication.getToken());
    }

    @Override
    public Observable<StateBean> getState(int nid) {
        return RetrofitFactory.getInstance().getCollectState("collect",nid,MyApplication.getToken());
    }
}