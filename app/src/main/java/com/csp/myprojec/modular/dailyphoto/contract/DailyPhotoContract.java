package com.csp.myprojec.modular.dailyphoto.contract;

import com.csp.myprojec.base.BaseView;
import com.csp.myprojec.bean.StateBean;
import com.csp.myprojec.bean.NewsBean;

import java.util.List;

import rx.Observable;

/**
 * Created by CSP on 2017/3/22.
 */

public interface DailyPhotoContract {

    //View层接口
    interface View extends BaseView {
        void showPhotoList(List<NewsBean.DataBean> newsList);//显示图片

        void showCollectState(StateBean stateBean, int position);//显示收藏状态

        void showLoadFailMsg(String msg);
    }

    //Presenter层接口
    interface Presenter {
        void loadPhotoList(int page);//载入、获取图片

        void collectPhoto(int nid, int position);//收藏图片
    }

    //Model层接口
    interface Model {
        Observable<NewsBean> getPhotoList(int page);

        Observable<StateBean> getState(int nid);
    }

}