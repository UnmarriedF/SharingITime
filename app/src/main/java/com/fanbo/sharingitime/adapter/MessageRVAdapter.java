package com.fanbo.sharingitime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fanbo on 2017-08-04.
 */

public class MessageRVAdapter extends RecyclerView.Adapter<MessageRVAdapter.ViewHolder>{

    private Context mContext;
    private List<Object> list;

    public MessageRVAdapter(Context mContext) {
        if (list==null){
            list = new ArrayList<>();
        }
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
