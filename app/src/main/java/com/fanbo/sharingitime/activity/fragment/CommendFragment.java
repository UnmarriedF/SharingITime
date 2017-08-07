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
import com.fanbo.sharingitime.adapter.ConcertRVAdapter;
import com.fanbo.sharingitime.biz.ShareBiz;
import com.fanbo.sharingitime.entity.TitleNews;

import rx.Subscriber;

/**
 * A simple {@link Fragment} subclass.
 */
public class CommendFragment extends BaseFragment {
    private ListView lv;
    private ConcertRVAdapter adapter;
    private ShareBiz shareBiz;
    private SwipeRefreshLayout sr;
    private Subscriber subscriber;
    private static int start = 0;

    public CommendFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_commend, container, false);
        initView();
        addListener();
        return contentView;
    }
    private void initView() {
        sr = (SwipeRefreshLayout) contentView.findViewById(R.id.sr_concert);
        lv = (ListView) contentView.findViewById(R.id.lv_concert);
        adapter = new ConcertRVAdapter(getActivity());
        lv.setAdapter(adapter);
        shareBiz = new ShareBiz();
        getData(start);
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
            }
        });
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
        shareBiz.getTitleNewsDateRETROFIT(subscriber, start, 10, "科技");
    }

}
