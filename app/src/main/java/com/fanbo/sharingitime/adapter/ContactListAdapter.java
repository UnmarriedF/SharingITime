package com.fanbo.sharingitime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *  联系人列表适配器
 *  Created by fanbo on 2017/6/20.
 */

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {
    private Context mContext;
    private List<UserEntity> userEntities;
    public ContactListAdapter(Context context) {
        if (userEntities==null){
            userEntities = new ArrayList<>();
        }
        mContext = context;
    }

    public void addData(List<UserEntity> data){
        userEntities = data;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //实例化view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        UserEntity userEntity = userEntities.get(position);
        Glide.with(mContext).load(userEntity.getHeaderPath()).into(holder.civ);
        holder.tvName.setText(userEntity.getUsername());
    }

    @Override
    public int getItemCount() {
        return userEntities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CircleImageView civ;
        CheckBox checkBox ;
        TextView tvName ;
        public ViewHolder(View itemView) {
            super(itemView);
            civ = (CircleImageView) itemView.findViewById(R.id.civ_head);
            checkBox = (CheckBox) itemView.findViewById(R.id.rbtn_contact_list_choice);
            tvName = (TextView) itemView.findViewById(R.id.tv_contact_list_name);
        }
    }
}
