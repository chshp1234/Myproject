package com.csp.myprojec.modular.news;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.csp.myprojec.R;
import com.csp.myprojec.activity.WebActivity;
import com.csp.myprojec.base.MvpFragment;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.modular.news.contract.NewsContract;
import com.csp.myprojec.modular.news.presenter.NewsPresenterImpl;
import com.github.lazylibrary.util.DateUtil;
import com.github.lazylibrary.util.NetWorkUtils;
import com.github.lazylibrary.util.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by CSP on 2017/1/19.
 */

public class NewsListFragment extends MvpFragment<NewsPresenterImpl> implements NewsContract.View, PtrHandler {
    @BindView(R.id.newslist)
    RecyclerView newslist;
    @BindView(R.id.store_house_ptr_frame)
    PtrFrameLayout storeHousePtrFrame;
    Unbinder unbinder;
    private StoreHouseHeader header;
    private NewsItemAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private boolean isShowTop = false;
    private int page = 1;
    private String category;
    private List<NewsBean.DataBean> dataList;

    private RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mLayoutManager.findFirstCompletelyVisibleItemPosition() == 0) {
                isShowTop = true;
            } else {
                isShowTop = false;
            }
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!recyclerView.canScrollVertically(1)) {
                mvpPresenter.loadNewsList(page, category);
            }
        }
    };

    public static NewsListFragment newInstance(String category) {
        Bundle args = new Bundle();
        NewsListFragment fragment = new NewsListFragment();
        args.putString("category", category);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        category = getArguments().getString("category");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.newslist_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        header = new StoreHouseHeader(MyApplication.getContext());
        header.initWithString("HELLOWORLD");
        header.setPadding(0, 10, 0, 10);
        header.setTextColor(Color.BLACK);
        storeHousePtrFrame.setDurationToCloseHeader(3000);
        storeHousePtrFrame.setHeaderView(header);
        storeHousePtrFrame.addPtrUIHandler(header);
        storeHousePtrFrame.setPtrHandler(this);
        storeHousePtrFrame.disableWhenHorizontalMove(true);
        mAdapter = new NewsItemAdapter(getActivity().getApplicationContext());
        mAdapter.setmOnItemClickListener((view1, position) -> {
            ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(view1,
                    view1.getWidth() / 2, view1.getHeight() / 2, 0, 0);
            String content = dataList.get(position).getContent();
            Intent intent = new Intent(getActivity().getApplicationContext(), WebActivity.class);
            intent.putExtra("content", content);
            intent.putExtra("nid",dataList.get(position).getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            ActivityCompat.startActivity(getActivity().getApplicationContext(), intent, options.toBundle());
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        newslist.setHasFixedSize(true);
        newslist.setLayoutManager(mLayoutManager);
        newslist.setAdapter(mAdapter);
        newslist.addOnScrollListener(mOnScrollListener);
        onRefreshBegin(storeHousePtrFrame);
    }


    @Override
    protected NewsPresenterImpl createPresenter() {
        return new NewsPresenterImpl(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        return isShowTop;
    }

    @Override
    public void onRefreshBegin(PtrFrameLayout frame) {
        if (NetWorkUtils.getNetworkType(MyApplication.getContext()) == -1) {
            ToastUtils.showToast(MyApplication.getContext(), "加载失败，请检查网络");
        } else {
            Log.i("onRefreshBegin", "begin");
            page = 1;
//        adapter.notifyDataSetChanged();
            if (dataList != null) {
                dataList.clear();
            }
            mvpPresenter.loadNewsList(page, category);
        }
        storeHousePtrFrame.refreshComplete();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNewsList(List<NewsBean.DataBean> newsList) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.addAll(newsList);
        if (page == 1) {
            mAdapter.setDataList(dataList);
            mAdapter.notifyDataSetChanged();
        } else {
            mAdapter.notifyDataSetChanged();
        }
        Log.i("showNews", "showNews");
        page++;
    }

    @Override
    public void showLoadFailMsg(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }
}
