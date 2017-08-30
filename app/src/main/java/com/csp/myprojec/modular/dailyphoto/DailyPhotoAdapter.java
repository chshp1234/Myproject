package com.csp.myprojec.modular.dailyphoto;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csp.myprojec.R;
import com.csp.myprojec.activity.ShowPhotoAcitvity;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.modular.comment.CommentWriteFragment;
import com.github.lazylibrary.util.StringUtils;
import com.github.lazylibrary.util.ToastUtils;
import com.sackcentury.shinebuttonlib.ShineButton;
import com.umeng.socialize.shareboard.SocializeImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CSP on 2017/1/25.
 */

public class DailyPhotoAdapter extends RecyclerView.Adapter {
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean mShowFooter = true;
    private Context mContext;
    private List<NewsBean.DataBean> dataList;

    public void setDataList(List<NewsBean.DataBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dailyphoto, parent, false);
            MyViewHolder viewHolder = new MyViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_dailyphoto, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            return new FooterViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder myViewHolder = (MyViewHolder) holder;
            NewsBean.DataBean data = dataList.get(position);
            Glide.with(MyApplication.getContext()).load(data.getThumb())
                    .placeholder(R.mipmap.placeholder)
                    .fitCenter()
                    .crossFade().into(myViewHolder.thumb);
            myViewHolder.title.setText(data.getTitle());
            myViewHolder.description.setText(data.getDescription());
            myViewHolder.inputtime.setText(data.getInputtime());
            myViewHolder.thumb.setOnClickListener(v -> {
                ActivityOptionsCompat options = ActivityOptionsCompat.makeScaleUpAnimation(myViewHolder.thumb,
                        myViewHolder.thumb.getWidth() / 2, myViewHolder.thumb.getHeight() / 2, 0, 0);
                Intent intent = new Intent(MyApplication.getContext(), ShowPhotoAcitvity.class);
                intent.putExtra("content", data.getContent());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ActivityCompat.startActivity(MyApplication.getContext(), intent, options.toBundle());
            });
            myViewHolder.commentShow.setOnClickListener(v -> {
                if (onGetCommentListener!=null){
                    onGetCommentListener.onClick(v,position);
                }
            });
            Log.i("token", MyApplication.getToken());
            if (data.getIsCollect() == 1) {
                myViewHolder.buttonCollect.setChecked(true);
            } else {
                myViewHolder.buttonCollect.setChecked(false);
            }

            if (StringUtils.isBlank(MyApplication.getToken())) {
                myViewHolder.buttonCollect.setOnClickListener(v -> {
                    Log.i("click", "click");
                    myViewHolder.buttonCollect.setChecked(false);
                    ToastUtils.showToast(MyApplication.getContext(), "请登录后再收藏");
                });
                myViewHolder.share_btn.setOnClickListener(v -> {
                    Log.i("click", "click");
                    ToastUtils.showToast(MyApplication.getContext(), "请登录后再收藏");
                });
            } else {
                myViewHolder.buttonCollect.setOnClickListener(v -> {
                    if (onShineButtonClickListener != null) {
                        onShineButtonClickListener.onClick(v, position);
                    }
                });
                myViewHolder.share_btn.setOnClickListener(v -> {
                    if(onShareListener!=null){
                        onShareListener.onShare(v,position);
                    }
                });
            }
            myViewHolder.comment.setOnClickListener(v ->{
                if (onCommentListener!=null){
                    onCommentListener.onClick(v,position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        if (!mShowFooter) {
            return TYPE_ITEM;
        }
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        int begin = mShowFooter ? 1 : 0;
        if (dataList == null) {
            return begin;
        }
        return dataList.size() + begin;
    }

    interface commentListener {
        void onClick(View view, int position);
    }

    interface imageClickListener {
        void onClick(View view, int position);
    }

    interface shineButtonClickListener {
        void onClick(View view, int position);
    }

    interface getCommentListener{
        void onClick(View view,int position);
    }

    interface shareListener{
        void onShare(View view,int position);
    }

    private commentListener onCommentListener;

    private getCommentListener onGetCommentListener;

    private shareListener onShareListener;

    public shareListener getOnShareListener() {
        return onShareListener;
    }

    public void setOnShareListener(shareListener onShareListener) {
        this.onShareListener = onShareListener;
    }

    public getCommentListener getOnGetCommentListener() {
        return onGetCommentListener;
    }

    public void setOnGetCommentListener(getCommentListener onGetCommentListener) {
        this.onGetCommentListener = onGetCommentListener;
    }

    public commentListener getOnCommentListener() {
        return onCommentListener;
    }

    public void setOnCommentListener(commentListener onCommentListener) {
        this.onCommentListener = onCommentListener;
    }

    private imageClickListener onImageClickListener;

    private shineButtonClickListener onShineButtonClickListener;

    public imageClickListener getOnImageClickListener() {
        return onImageClickListener;
    }

    public void setOnImageClickListener(imageClickListener onImageClickListener) {
        this.onImageClickListener = onImageClickListener;
    }

    public shineButtonClickListener getOnShineButtonClickListener() {
        return onShineButtonClickListener;
    }

    public void setOnShineButtonClickListener(shineButtonClickListener onShineButtonClickListener) {
        this.onShineButtonClickListener = onShineButtonClickListener;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.thumb)
        ImageView thumb;
        @BindView(R.id.share_btn)
        SocializeImageView share_btn;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.inputtime)
        TextView inputtime;
        @BindView(R.id.comment)
        Button comment;
        @BindView(R.id.button_collect)
        ShineButton buttonCollect;
        @BindView(R.id.comment_show)
        AppCompatImageButton commentShow;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
