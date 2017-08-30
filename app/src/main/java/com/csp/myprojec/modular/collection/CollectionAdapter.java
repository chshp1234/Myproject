package com.csp.myprojec.modular.collection;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.csp.myprojec.R;
import com.csp.myprojec.base.MyApplication;
import com.csp.myprojec.bean.NewsBean;
import com.csp.myprojec.custom.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnLongClick;

/**
 * Created by CSP on 2017/3/23.
 */

public class CollectionAdapter extends RecyclerView.Adapter {

    private boolean flag = false;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private boolean mShowFooter = true;
    private List<NewsBean.DataBean> dataList;
    private OnItemDeleteListener onItemDeleteListener;
    private OnItemLongClickListener onItemLongClickListener;
    private OnItemClickListener onItemClickListener;

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemLongClickListener getOnItemLongClickListener() {
        return onItemLongClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public OnItemDeleteListener getOnItemDeleteListener() {
        return onItemDeleteListener;
    }

    public void setOnItemDeleteListener(OnItemDeleteListener onItemDeleteListener) {
        this.onItemDeleteListener = onItemDeleteListener;
    }

    public boolean ismShowFooter() {
        return mShowFooter;
    }

    public void setmShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
    }

    public static int getTypeItem() {
        return TYPE_ITEM;
    }

    public static int getTypeFooter() {
        return TYPE_FOOTER;
    }

    public List<NewsBean.DataBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<NewsBean.DataBean> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collection, null);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.footer_vertical, null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            NewsBean.DataBean data = dataList.get(position);
            Glide.with(MyApplication.getContext()).load(data.getThumb())
                    .placeholder(R.mipmap.placeholder)
                    .fitCenter()
                    .crossFade().into(viewHolder.collectionImage);
            viewHolder.collectionTitle.setText(data.getTitle());
            if (!flag) {
                viewHolder.delete.setVisibility(View.GONE);
            } else {
                viewHolder.delete.setVisibility(View.VISIBLE);
                viewHolder.delete.setOnClickListener(v -> {
                    if (onItemDeleteListener != null) {
                        onItemDeleteListener.onItemDelete(v, position);
                    }
                });
            }
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

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener,View.OnClickListener {
        @BindView(R.id.collection_image)
        ImageView collectionImage;
        @BindView(R.id.collection_title)
        TextView collectionTitle;
        @BindView(R.id.delete)
        ImageView delete;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnLongClickListener(this);
            view.setOnClickListener(this);
        }

        @Override
        public boolean onLongClick(View v) {
            if (onItemLongClickListener!=null){
                onItemLongClickListener.onItemLongClick(v,this.getPosition());
            }
            return true;
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener!=null){
                onItemClickListener.onItemClick(v,this.getPosition());
            }
        }
    }

    interface OnItemDeleteListener {
        void onItemDelete(View view, int index);
    }

    interface OnItemLongClickListener {
        void onItemLongClick(View view, int index);
    }
}
