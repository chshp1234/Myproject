package com.csp.myprojec.modular.news.model;

import android.util.Log;

import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.custom.RetrofitFactory;
import com.csp.myprojec.modular.news.contract.NewsContract;

import rx.Observable;

/**
 * Created by CSP on 2017/03/12
 */

public class NewsModelImpl implements NewsContract.Model {

    @Override
    public Observable<NewsBean> getNewsList(int page, String category) {
        Log.i("category",category);
        return RetrofitFactory.getInstance().getNews("news", page, category, MyApplication.getToken());
    }
}