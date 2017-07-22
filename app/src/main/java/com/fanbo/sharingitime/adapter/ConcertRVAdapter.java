package com.fanbo.sharingitime.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.entity.TitleNews;
import com.fanbo.sharingitime.widget.CircleImageView;

/**
 * Created by fanbo on 2017-07-22.
 */

public class ConcertRVAdapter extends BaseAdapter<TitleNews.ResultBean.ListBean>{
    /**
     * 构造方法
     *
     * @param context 用于传递上下文参数
     */
    public ConcertRVAdapter(Context context) {
        super(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.concert_rv_itme,null);
            viewHolder = new ViewHolder();
            viewHolder.civ = (CircleImageView) convertView.findViewById(R.id.civ_head);
            viewHolder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.tvDate = (TextView) convertView.findViewById(R.id.tv_date);
            viewHolder.ivConcert = (ImageView) convertView.findViewById(R.id.iv_concert);
            viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
            viewHolder.ivLike = (ImageView) convertView.findViewById(R.id.iv_like);
            viewHolder.ivComments = (ImageView) convertView.findViewById(R.id.iv_comments);
            viewHolder.ivShare = (ImageView) convertView.findViewById(R.id.iv_share);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        TitleNews.ResultBean.ListBean listBean = mList.get(position);
        Glide.with(mContext).load(listBean.getPic()).into(viewHolder.civ);
        Glide.with(mContext).load(listBean.getPic()).into(viewHolder.ivConcert);
        viewHolder.tvName.setText(listBean.getSrc());
        viewHolder.tvDate.setText(listBean.getTime());
        viewHolder.tvTitle.setText(listBean.getTitle());
        return convertView;
    }
    class ViewHolder{
        CircleImageView civ;
        TextView tvName;
        TextView tvDate;
        ImageView ivConcert;
        TextView tvTitle;
        ImageView ivLike;
        ImageView ivComments;
        ImageView ivShare;
    }
}
