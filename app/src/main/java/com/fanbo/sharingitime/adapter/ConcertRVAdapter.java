package com.fanbo.sharingitime.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.WebWiewActivity;
import com.fanbo.sharingitime.entity.TitleNews;
import com.fanbo.sharingitime.widget.CircleImageView;

import cn.sharesdk.onekeyshare.OnekeyShare;

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
        final TitleNews.ResultBean.ListBean listBean = mList.get(position);
        Glide.with(mContext).load(listBean.getPic()).into(viewHolder.civ);
        Glide.with(mContext).load(listBean.getPic()).into(viewHolder.ivConcert);
        viewHolder.tvName.setText(listBean.getSrc());
        viewHolder.tvDate.setText(listBean.getTime());
        viewHolder.tvTitle.setText(listBean.getTitle());

        viewHolder.ivShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showShare(mContext);
            }
        });
        viewHolder.ivConcert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, WebWiewActivity.class);
                intent.putExtra("data",listBean.getUrl());
                mContext.startActivity(intent);
            }
        });
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
    private void showShare(Context context) {
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
        // 分享时Notification的图标和文字  2.5.9以后的版本不     调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle("时间分享");
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("大家一起来分享时间吧");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("哈哈哈");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(context.getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(context);
    }

}
