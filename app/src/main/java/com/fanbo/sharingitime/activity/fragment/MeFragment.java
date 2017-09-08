package com.fanbo.sharingitime.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.biz.MeBiz;
import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.util.ExceptionUtil;
import com.fanbo.sharingitime.widget.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {


    private CircleImageView civ;
    private TextView tvName;
    private MeBiz meBiz;

    public MeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_me, container, false);
        initView();
        addListener();
        return contentView;
    }

    private void addListener() {

    }

    private void initView() {
        civ = (CircleImageView) contentView.findViewById(R.id.civ_head);
        tvName = (TextView) contentView.findViewById(R.id.tv_name);
        meBiz = new MeBiz();
        UserEntity userEntity = meBiz.getUser();
        tvName.setText(userEntity.getUsername());
        Glide.with(this).load(userEntity.getHeaderImg()).into(civ);
    }

}
