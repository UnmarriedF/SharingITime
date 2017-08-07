package com.fanbo.sharingitime.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.MyApplication;
import com.fanbo.sharingitime.adapter.ConcertRVAdapter;
import com.fanbo.sharingitime.biz.ShareBiz;
import com.fanbo.sharingitime.entity.TitleNews;
import com.fanbo.sharingitime.util.Const;
import com.mob.MobSDK;

import cn.sharesdk.onekeyshare.OnekeyShare;
import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConcertFragment extends BaseFragment {


    private ListView lv;
    private ConcertRVAdapter adapter;
    private ShareBiz shareBiz;
    private SwipeRefreshLayout sr;
    private Subscriber subscriber;
    private static int start = 0;

    public ConcertFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_concert, container, false);
        initView();
        addListener();
        return contentView;
    }

    private void addListener() {
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                start = start + 10;
                getData(start);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showShare();
            }
        });
    }

    private void initView() {
        sr = (SwipeRefreshLayout) contentView.findViewById(R.id.sr_concert);
        lv = (ListView) contentView.findViewById(R.id.lv_concert);
        adapter = new ConcertRVAdapter(getActivity());
        lv.setAdapter(adapter);
        shareBiz = new ShareBiz();
        getData(start);
    }

    private void getData(final int start) {
        subscriber = new Subscriber<TitleNews>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(TitleNews titleNews) {
                adapter.addData(titleNews.getResult().getList(), true);
                sr.setRefreshing(false);
            }
        };
        shareBiz.getTitleNewsDateRETROFIT(subscriber, start, 10, "体育");
    }
    private void showShare() {
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
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
        // 启动分享GUI
        oks.show(getActivity());
    }

}
