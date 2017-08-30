package com.csp.myprojec.modular.dailyphoto.model;

import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.StateBean;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.custom.RetrofitFactory;
import com.csp.myprojec.modular.dailyphoto.contract.DailyPhotoContract;

import rx.Observable;

/**
 * Created by CSP on 2017/03/22
 */

public class DailyPhotoModelImpl implements DailyPhotoContract.Model {

    @Override
    public Observable<NewsBean> getPhotoList(int page) {
        return RetrofitFactory.getInstance().getDailyPhoto("pofday", page, MyApplication.getToken());
    }

    @Override
    public Observable<StateBean> getState(int nid) {
        return RetrofitFactory.getInstance().getCollectState("collect", nid, MyApplication.getToken());
    }
}