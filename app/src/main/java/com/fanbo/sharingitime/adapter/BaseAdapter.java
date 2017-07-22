package com.fanbo.sharingitime.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * 基础适配器
 * Created by fanbo on 2017/6/12.
 */

public abstract class BaseAdapter<T> extends android.widget.BaseAdapter {
    /**
     * 适配数据的集合
     */
    protected List<T> mList = new ArrayList<>();
    /**
     * 布局解析器
     */
    protected LayoutInflater layoutInflater = null;
    /**
     * 上下文对象
     */
    protected Context mContext = null;

    /**
     * 构造方法
     *
     * @param context 用于传递上下文参数
     */
    public BaseAdapter(Context context) {
        mContext = context;
        layoutInflater = LayoutInflater.from(context);
    }

    /**
     * @param list    要添加的数据
     * @param isClear 标识，是否要删除原有数据（清空原有的集合）
     */
    public void addData(List<T> list, boolean isClear) {
        if (isClear) {
            mList.clear();
        }
        if (list != null) {
            mList.addAll(list);
            //通知UI进行更新
            notifyDataSetChanged();
        }
    }

    /**
     * 移除适配器中的数据
     */
    public void removeData() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void removeData(T t) {
        mList.remove(t);
        notifyDataSetChanged();
    }

    /**
     * 查询数据
     *
     * @return 适配器中的所有数据
     */
    public List<T> queryData() {
        return mList;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public abstract View getView(int position, View convertView, ViewGroup parent);
}
