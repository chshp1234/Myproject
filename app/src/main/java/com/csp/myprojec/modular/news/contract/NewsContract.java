package com.csp.myprojec.modular.news.contract;

import com.csp.myprojec.base.BaseView;
import com.csp.myprojec.bean.NewsBean;

import java.util.List;

import rx.Observable;

/**
 * Created by CSP on 2017/3/12.
 */

public interface NewsContract {
    interface View extends BaseView {
        void showNewsList(List<NewsBean.DataBean> newsList);

        void showLoadFailMsg(String msg);
    }

    interface Presenter {
        void loadNewsList(int page, String category);
    }

    interface Model {
        Observable<NewsBean> getNewsList(int page, String category);
    }


}