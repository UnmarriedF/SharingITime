package com.fanbo.sharingitime.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.ContactActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivRight;

    public MessageFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_message, container, false);
        titlebar = (RelativeLayout) contentView.findViewById(R.id.title_bar);
        ivRight = (ImageView) titlebar.findViewById(R.id.iv_title_right);
        initView();
        addListener();
        return contentView;
    }

    private void addListener() {
        ivRight.setOnClickListener(this);
    }

    private void initView() {
        initTitleBar(0,"","消息","",R.drawable.ic_contact);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_title_right:
                //跳转到联系人列表界面
                startActivity(new Intent(getActivity(), ContactActivity.class));
                break;
        }
    }
}