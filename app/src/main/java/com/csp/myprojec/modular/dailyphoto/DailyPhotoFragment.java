package com.csp.myprojec.modular.dailyphoto;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.csp.myprojec.R;
import com.csp.myprojec.activity.LoginActivity;
import com.csp.myprojec.activity.MainActivity;
import com.csp.myprojec.base.Config;
import com.csp.myprojec.base.MvpFragment;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.StateBean;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.event.CommentEvent;
import com.csp.myprojec.modular.comment.CommentActivity;
import com.csp.myprojec.modular.comment.CommentWriteFragment;
import com.csp.myprojec.modular.dailyphoto.contract.DailyPhotoContract;
import com.csp.myprojec.modular.dailyphoto.presenter.DailyPhotoPresenterImpl;
import com.github.lazylibrary.util.PreferencesUtils;
import com.github.lazylibrary.util.StringUtils;
import com.github.lazylibrary.util.ToastUtils;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.editorpage.ShareActivity;
import com.umeng.socialize.media.UMImage;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DailyPhotoFragment extends MvpFragment<DailyPhotoPresenterImpl> implements DailyPhotoContract.View {

    @BindView(R.id.photolist)
    RecyclerView photolist;
    Unbinder unbinder;

    private List<NewsBean.DataBean> dataList;
    private LinearLayoutManager mLayoutManager;
    private int page = 1;
    private DailyPhotoAdapter mAdapter;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (!recyclerView.canScrollHorizontally(1)) {//水平判断，值为1表示可以往左滑动
                mvpPresenter.loadPhotoList(page);
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.daily_fragment, null);
        unbinder = ButterKnife.bind(this, mView);
        init();
        return mView;
    }

    private void init() {
        Log.i("ip_config", PreferencesUtils.getString(MyApplication.getContext(), "ip_config", "http://123.57.13.94/csp/"));
        mAdapter = new DailyPhotoAdapter();
        mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        photolist.setLayoutManager(mLayoutManager);

        mAdapter.setOnShineButtonClickListener((view, position) ->
                mvpPresenter.collectPhoto(dataList.get(position).getId(), position)
        );
        mAdapter.setOnCommentListener((view, position) -> {
            if (StringUtils.isBlank(MyApplication.getToken())) {
                ToastUtils.showToast(MyApplication.getContext(), "请登录后再评论");
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            } else {
//                EventBus.getDefault().post(new CommentEvent(dataList.get(position).getId(),0));
                CommentWriteFragment commentWriteFragment = CommentWriteFragment.newInstance(dataList.get(position).getId(), 0);
                commentWriteFragment.show(getActivity().getSupportFragmentManager(), "dialog");
            }
        });
        mAdapter.setOnGetCommentListener((view, position) -> {
            Intent intent = new Intent(getActivity(), CommentActivity.class);
            intent.putExtra("nid", dataList.get(position).getId());
            intent.putExtra("parentid", 0);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getActivity().startActivity(intent);
        });
        mAdapter.setOnShareListener((view, position) -> new ShareAction(getActivity())
                .withMedia(new UMImage(getActivity(), dataList.get(position).getThumb()))
                .withText(dataList.get(position).getTitle())
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.WEIXIN_FAVORITE)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(SHARE_MEDIA share_media) {
                        Log.d("plat", "platform" + share_media);
                        ToastUtils.showToast(MyApplication.getContext(), "分享成功了！");
                    }

                    @Override
                    public void onError(SHARE_MEDIA share_media, Throwable throwable) {
                        ToastUtils.showToast(MyApplication.getContext(), "分享失败了！");
                        if (throwable != null) {
                            Log.d("throw", "throw:" + throwable.getMessage());
                        }
                    }

                    @Override
                    public void onCancel(SHARE_MEDIA share_media) {
                        ToastUtils.showToast(MyApplication.getContext(), "分享取消了！");
                    }
                }).open());

        photolist.setAdapter(mAdapter);
        new LinearSnapHelper().attachToRecyclerView(photolist);
        photolist.addOnScrollListener(onScrollListener);
        mvpPresenter.loadPhotoList(page);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    protected DailyPhotoPresenterImpl createPresenter() {
        return new DailyPhotoPresenterImpl(this);
    }

    @Override
    public void showPhotoList(List<NewsBean.DataBean> newsList) {
        if (dataList == null) {
            dataList = new ArrayList<>();
        }
        dataList.addAll(newsList);
        mAdapter.setDataList(dataList);
        mAdapter.notifyDataSetChanged();
        Log.i("showNews", "showNews");
        page++;
    }

    @Override
    public void showCollectState(StateBean stateBean, int position) {
        if (stateBean.getCode() == 200) {
            dataList.get(position).setIsCollect(1);
            ToastUtils.showToast(MyApplication.getContext(), "收藏成功");
        } else if (stateBean.getCode() == 201) {
            dataList.get(position).setIsCollect(0);
            ToastUtils.showToast(MyApplication.getContext(), "取消收藏成功");
        }
    }

    @Override
    public void showLoadFailMsg(String msg) {
        ToastUtils.showToast(getActivity(), msg);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
