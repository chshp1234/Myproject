package com.csp.myprojec.modular.collection;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;

import com.csp.myprojec.R;
import com.csp.myprojec.activity.ShowPhotoAcitvity;
import com.csp.myprojec.base.MvpActivity;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.modular.collection.contract.CollectionContract;
import com.csp.myprojec.modular.collection.presenter.CollectionPresenterImpl;
import com.github.lazylibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.R.attr.data;

/**
 * Created by CSP on 2017/3/23.
 */

public class CollectionActivity extends MvpActivity<CollectionPresenterImpl> implements CollectionContract.View {


    @BindView(R.id.collection)
    RecyclerView collection;

    private StaggeredGridLayoutManager layoutManager;
    private List<NewsBean.DataBean> dataList;
    private int page = 1;
    private CollectionAdapter mAdapter;
    private boolean isMoreData = true;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!recyclerView.canScrollVertically(1) && isMoreData) {
                mvpPresenter.loadCollection(page);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collection);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        mAdapter = new CollectionAdapter();
        mAdapter.setOnItemLongClickListener((view, index) -> {//长按点击事件
            mAdapter.setFlag(true);
            mAdapter.notifyDataSetChanged();
        });
        mAdapter.setOnItemClickListener((view, position) -> {//单击点击事件
            ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view,
                    view.getWidth() / 2, view.getHeight() / 2, 0, 0);
            Intent intent = new Intent(CollectionActivity.this, ShowPhotoAcitvity.class);
            intent.putExtra("content", dataList.get(position).getContent());
            ActivityCompat.startActivity(CollectionActivity.this, intent, options.toBundle());
        });
        mAdapter.setOnItemDeleteListener((view, index) -> {
            mvpPresenter.deleteItem(dataList.get(index).getId());
            dataList.remove(index);
            mAdapter.notifyItemRemoved(index);
            mAdapter.notifyItemRangeChanged(index,dataList.size()-1);
        });

//        layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        collection.setLayoutManager(new GridLayoutManager(this, 2));
        collection.setAdapter(mAdapter);
        collection.addOnScrollListener(mOnScrollListener);
        collection.setHasFixedSize(true);
        collection.setItemAnimator(new DefaultItemAnimator());
        mvpPresenter.loadCollection(page);
    }


    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showCollection(List<NewsBean.DataBean> collectionList) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.addAll(collectionList);
        mAdapter.setDataList(dataList);
        if (collectionList == null || collectionList.size() == 0) {
            ToastUtils.showToast(this, "没有更多收藏项啦");
            isMoreData = false;
        }
        mAdapter.notifyDataSetChanged();
        Log.i("showNews", "showNews");
        page++;
    }

    @Override
    public void showLoadFailMsg(String msg) {
        ToastUtils.showToast(MyApplication.getContext(), msg);

    }

    @Override
    public void showItemDelete(String msg) {

        ToastUtils.showToast(MyApplication.getContext(), msg);
    }

    @Override
    protected CollectionPresenterImpl createPresenter() {
        return new CollectionPresenterImpl(this);
    }

    @Override
    public void onBackPressed() {
        if (mAdapter.isFlag()) {
            mAdapter.setFlag(false);
            mAdapter.notifyDataSetChanged();
        } else {
            super.onBackPressed();
            finish();
        }
    }
}
