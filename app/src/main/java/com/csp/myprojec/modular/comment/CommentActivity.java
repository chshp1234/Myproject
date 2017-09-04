package com.csp.myprojec.modular.comment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.csp.myprojec.R;
import com.csp.myprojec.base.MvpActivity;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.CommentBean;
import com.csp.myprojec.custom.onItemChangeListener;
import com.csp.myprojec.event.DataChangeEvent;
import com.csp.myprojec.modular.comment.contract.CommentContract;
import com.csp.myprojec.modular.comment.presenter.CommentListAdapter;
import com.csp.myprojec.modular.comment.presenter.CommentPresenterImpl;
import com.github.lazylibrary.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by CSP on 2017/4/17.
 */

public class CommentActivity extends MvpActivity<CommentPresenterImpl> implements CommentContract.View {

    @BindView(R.id.comment_list)
    RecyclerView commentList;
    private int nid;
    private int parentid;
    private LinearLayoutManager mManager;
    private List<CommentBean.DataBean> dataList;
    private CommentListAdapter mAdapter;
    private int page;
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
                mvpPresenter.loadComment(nid, parentid, page);
                mAdapter.setmShowFooter(false);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        init();
        mvpPresenter.loadComment(nid, parentid, page);
    }

    private void init() {
        page = 1;
        nid = getIntent().getIntExtra("nid", 0);
        parentid = getIntent().getIntExtra("parentid", 0);
        mManager = new LinearLayoutManager(CommentActivity.this);
        mAdapter = new CommentListAdapter();
        mAdapter.setOnItemClickListener((view, position) -> {
            if (dataList.get(position).getCount() == 0) {
                CommentWriteFragment commentWriteFragment = CommentWriteFragment.newInstance(nid, dataList.get(position).getId());
                commentWriteFragment.show(getSupportFragmentManager(), "user_comment");
            } else {
                Intent intent = new Intent(CommentActivity.this, CommentActivity.class);
                intent.putExtra("nid", nid);
                intent.putExtra("parentid", dataList.get(position).getId());
                startActivity(intent);
            }
        });
        commentList.setLayoutManager(mManager);
        commentList.setAdapter(mAdapter);
        commentList.addOnScrollListener(mOnScrollListener);
    }

    @Override
    protected CommentPresenterImpl createPresenter() {
        return new CommentPresenterImpl(this);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showComment(List<CommentBean.DataBean> dataBean) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        mAdapter.setmShowFooter(true);
        dataList.addAll(dataBean);
        mAdapter.setDataList(dataList);
        if (dataBean == null || dataBean.size() == 0) {
            isMoreData = false;
            mAdapter.setmShowFooter(false);
        }
        mAdapter.notifyDataSetChanged();
        page++;
    }

    @Override
    public void showLoadFailMsg(String msg) {
        ToastUtils.showToast(MyApplication.getContext(), msg);

    }

    @OnClick(R.id.comment)
    public void onClick() {
        CommentWriteFragment commentWriteFragment = CommentWriteFragment.newInstance(nid, parentid);
        commentWriteFragment.show(getSupportFragmentManager(), "news_comment");
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void reciveMessge(DataChangeEvent DataChangeEvent) {
        Log.i("DataChangeEvent", "DataChangeEvent");
        page = 1;
        if (dataList != null) {
            dataList.clear();
        }
        isMoreData=true;
        mvpPresenter.loadComment(nid,parentid, page);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
