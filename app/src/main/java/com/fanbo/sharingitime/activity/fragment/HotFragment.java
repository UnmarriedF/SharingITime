package com.fanbo.sharingitime.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.adapter.HeaderRVAdapter;
import com.fanbo.sharingitime.biz.ShareBiz;
import com.fanbo.sharingitime.entity.TitleNews;
import com.fanbo.sharingitime.util.ToastUtil;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends BaseFragment implements ShareBiz.GetTitleNewsListener {


    private SwipeRefreshLayout sr;
    private RecyclerView rv;
    private HeaderRVAdapter headerRVAdapter;
    private GridLayoutManager gridLayoutManager;
    private ShareBiz shareBiz;
    private int start = 0;

    public HotFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_hot, container, false);
        initView();
        addListener();
        return contentView;
    }

    private void addListener() {
        sr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                shareBiz.getTitleNewsDateOKHTTP(start, 10, "新闻", HotFragment.this);
            }
        });
    }

    private void initView() {
        shareBiz = new ShareBiz();
        sr = (SwipeRefreshLayout) contentView.findViewById(R.id.sw_hot);
        sr.setColorSchemeResources(R.color.mainblue,R.color.limegreen,R.color.colorAccent);
        rv = (RecyclerView) contentView.findViewById(R.id.rv_hot);
        headerRVAdapter = new HeaderRVAdapter(getActivity());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rv.setLayoutManager(gridLayoutManager);
        rv.setAdapter(headerRVAdapter);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return (headerRVAdapter.isHeaderView(position) || headerRVAdapter.isBottomView(position))
                        ? gridLayoutManager.getSpanCount() : 1;
            }
        });
        //获取数据
        shareBiz.getTitleNewsDateOKHTTP(start, 10, "娱乐", this);
    }

    @Override
    public void onDataGetEnd(final List<TitleNews.ResultBean.ListBean> listBeen) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    headerRVAdapter.addData(listBeen);
                    sr.setRefreshing(false);
                    start = start + 10;
                }
            });
    }
      
}
