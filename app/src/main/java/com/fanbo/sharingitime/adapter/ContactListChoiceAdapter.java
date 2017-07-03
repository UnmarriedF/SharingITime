package com.fanbo.sharingitime.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.widget.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 *  联系人选择中列表显示适配器
 *  Created by fanbo on 2017/7/2.
 */

public class ContactListChoiceAdapter  extends RecyclerView.Adapter<ContactListChoiceAdapter.ViewHolder>{
    private Context mContext;
    private List<UserEntity> userEntityList;

    public ContactListChoiceAdapter(Context context) {
        mContext = context;
        if (userEntityList==null){
            userEntityList=new ArrayList<>();
        }
    }

    /**
     * 添加联系人头像到选中列表
     * @param userEntity
     */
    public void addDate(UserEntity userEntity){
        if (userEntity!=null){
            userEntityList.add(userEntity);
        }
        notifyDataSetChanged();
    }

    /**
     * 清空选中列表
     */
    public void clearList(){
        if (userEntityList.size()>0){
        userEntityList.clear();
        }
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_choice,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).load(userEntityList.get(position).getHeaderPath()).into(holder.civ);
    }

    @Override
    public int getItemCount() {
        return userEntityList==null?0:userEntityList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civ;
        public ViewHolder(View itemView) {
            super(itemView);
            civ = (CircleImageView) itemView.findViewById(R.id.civ_head);
        }
    }
}
