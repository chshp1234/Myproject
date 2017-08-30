package com.csp.myprojec.modular.comment.presenter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.csp.myprojec.R;
import com.csp.myprojec.bean.CommentBean;
import com.csp.myprojec.custom.OnItemClickListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by CSP on 2017/4/17.
 */

public class CommentListAdapter extends RecyclerView.Adapter {
    private int count;
    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private List<CommentBean.DataBean> dataList;
    private OnItemClickListener onItemClickListener;
    private boolean mShowFooter = true;

    public boolean ismShowFooter() {
        return mShowFooter;
    }

    public void setmShowFooter(boolean mShowFooter) {
        this.mShowFooter = mShowFooter;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<CommentBean.DataBean> getDataList() {
        return dataList;
    }

    public void setDataList(List<CommentBean.DataBean> dataList) {
        this.dataList = dataList;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, null);
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
            viewHolder.nickname.setText(dataList.get(position).getNickname());
            viewHolder.content.setText(dataList.get(position).getContent());
            viewHolder.commentTime.setText(dataList.get(position).getCommenttime());
            viewHolder.count.setText(dataList.get(position).getCount()+"");
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

    public class FooterViewHolder extends RecyclerView.ViewHolder {

        public FooterViewHolder(View view) {
            super(view);
        }

    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.nickname)
        TextView nickname;
        @BindView(R.id.content)
        TextView content;
        @BindView(R.id.comment_time)
        TextView commentTime;
        @BindView(R.id.count)
        TextView count;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, this.getPosition());
            }
        }
    }
}
