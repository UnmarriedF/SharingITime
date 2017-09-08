package com.fanbo.sharingitime.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.ContactActivity;
import com.fanbo.sharingitime.activity.MainActivity;
import com.fanbo.sharingitime.biz.MessageBiz;
import com.fanbo.sharingitime.util.Const;
import com.fanbo.sharingitime.util.ExceptionUtil;
import com.hyphenate.chat.EMConversation;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends BaseFragment implements View.OnClickListener {

    private ImageView ivRight;
    private MessageBiz messageBiz;
    private Map<String, EMConversation> messageList;
    private MessageHandler messageHandler;

    public MessageFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        try {
        contentView = inflater.inflate(R.layout.fragment_message, container, false);
        titlebar = (RelativeLayout) contentView.findViewById(R.id.title_bar);
        ivRight = (ImageView) titlebar.findViewById(R.id.iv_title_right);
        initView();
            addListener();
        } catch (Exception e) {
            ExceptionUtil.handleException(e);
        }
        return contentView;
    }


    private void addListener() {
        ivRight.setOnClickListener(this);
    }

    private void initView() {
        initTitleBar(0,"","消息","",R.drawable.ic_contact);
        messageHandler = new MessageHandler();
        messageBiz = new MessageBiz(messageHandler);
        messageBiz.getChatList();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_title_right:
                //跳转到联系人列表界面
                startActivity(new Intent(getActivity(), ContactActivity.class));
                break;
            case R.id.iv_back:
                getActivity().onBackPressed();
        }
    }

    class MessageHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Const.HX_MESSAGE_LIST_SUCCESS:
                    messageList = (Map<String, EMConversation>) msg.obj;
                    if(messageList==null){
                        getActivity().startActivity(new Intent(getActivity(),ContactActivity.class));
                    }
            }
        }
    }
}