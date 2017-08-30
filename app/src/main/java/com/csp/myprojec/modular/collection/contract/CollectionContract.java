package com.csp.myprojec.modular.collection.contract;

import com.csp.myprojec.base.BaseView;
import com.csp.myprojec.bean.StateBean;
import com.csp.myprojec.bean.NewsBean;

import java.util.List;

import rx.Observable;

/**
 * Created by CSP on 2017/3/23.
 */

public interface CollectionContract {

    interface View extends BaseView {
        void showCollection(List<NewsBean.DataBean> collectionList);

        void showLoadFailMsg(String msg);

        void showItemDelete(String msg);
    }

    interface Presenter {
        void loadCollection(int page);
        void deleteItem(int nid);
    }

    interface Model {
        Observable<NewsBean> getCollection(int page);
        Observable<StateBean> getState(int nid);
    }
}