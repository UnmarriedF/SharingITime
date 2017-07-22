package com.fanbo.sharingitime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.biz.ShareBiz;
import com.fanbo.sharingitime.entity.TitleNews;
import com.fanbo.sharingitime.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by fanbo on 2017-07-21.
 */

public class HeaderRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    //item类型
    public static final int ITEM_TYPE_HEADER = 0;
    public static final int ITEM_TYPE_CONTENT = 1;
    public static final int ITEM_TYPE_BOTTOM = 2;
    //模拟数据(以新闻数据替代分享数据)
    private List<TitleNews.ResultBean.ListBean> listBeen;
    private LayoutInflater mLayoutInflater;
    private Context mContext;
    private int mHeaderCount = 1;//头部View个数
    private int mBottomCount = 0;//底部View个数
    public HeaderRVAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        listBeen = new ArrayList<>();
    }

    //内容长度
    public int getContentItemCount() {
        return listBeen==null?0:listBeen.size();
    }

    //判断当前item是否是HeadView
    public boolean isHeaderView(int position) {
        return mHeaderCount != 0 && position < mHeaderCount;
    }

    //判断当前item是否是FooterView
    public boolean isBottomView(int position) {
        return mBottomCount != 0 && position >= (mHeaderCount + getContentItemCount());
    }

    //判断当前item类型
    @Override
    public int getItemViewType(int position) {
        int dataItemCount = getContentItemCount();
        if (mHeaderCount != 0 && position < mHeaderCount) {
        //头部View
            return ITEM_TYPE_HEADER;
        } else if (mBottomCount != 0 && position >= (mHeaderCount + dataItemCount)) {
        //底部View
            return ITEM_TYPE_BOTTOM;
        } else {
        //内容View
            return ITEM_TYPE_CONTENT;
        }
    }

    //内容 ViewHolder
    public static class ContentViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPhoto;
        CircleImageView civHeader;
        TextView tvName;
        TextView tvTitle;
        TextView tvWhatchCount;
        public ContentViewHolder(View itemView) {
            super(itemView);
            ivPhoto = (ImageView) itemView.findViewById(R.id.iv_photo);
            civHeader = (CircleImageView) itemView.findViewById(R.id.circleImageView);
            tvName = (TextView) itemView.findViewById(R.id.tv_name);
            tvTitle = (TextView) itemView.findViewById(R.id.tv_title);
            tvWhatchCount = (TextView) itemView.findViewById(R.id.tv_watch_count);
        }
    }
    //头部 ViewHolder
    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    //底部 ViewHolder
    public static class BottomViewHolder extends RecyclerView.ViewHolder {
        public BottomViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_HEADER) {
            return new HeaderViewHolder(mLayoutInflater.inflate(R.layout.hot_rv_header, parent, false));
        } else if (viewType == mHeaderCount) {
            return new ContentViewHolder(mLayoutInflater.inflate(R.layout.hot_rv_item, parent, false));
        } else if (viewType == ITEM_TYPE_BOTTOM) {
            return new BottomViewHolder(mLayoutInflater.inflate(R.layout.hot_rv_bottom, parent, false));
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof HeaderViewHolder) {

        } else if (holder instanceof ContentViewHolder) {
            TitleNews.ResultBean.ListBean listBean = listBeen.get(position-mHeaderCount);
            if (TextUtils.isEmpty(listBean.getPic())){
            Glide.with(mContext).load(R.drawable.aaaaa).into(((ContentViewHolder) holder).ivPhoto);
            Glide.with(mContext).load(R.drawable.aaaaa).into(((ContentViewHolder) holder).civHeader);
            }else {
            Glide.with(mContext).load(listBean.getPic()).into(((ContentViewHolder) holder).ivPhoto);
            Glide.with(mContext).load(listBean.getPic()).into(((ContentViewHolder) holder).civHeader);
            }
            ((ContentViewHolder) holder).tvTitle.setText(listBean.getTitle());
            ((ContentViewHolder) holder).tvName.setText(listBean.getSrc());
            Random random = new Random();
            ((ContentViewHolder) holder).tvWhatchCount.setText(String.valueOf(random.nextInt(1000)));
        } else if (holder instanceof BottomViewHolder) {
        }
    }

    @Override
    public int getItemCount() {
        return mHeaderCount + getContentItemCount() + mBottomCount;
    }
    public void addData(List<TitleNews.ResultBean.ListBean> listBeen){
        this.listBeen = listBeen;
        notifyDataSetChanged();
    }
}
