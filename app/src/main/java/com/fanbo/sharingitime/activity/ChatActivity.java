package com.fanbo.sharingitime.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.biz.ChatBiz;
import com.fanbo.sharingitime.entity.UserEntity;


import butterknife.BindView;
import butterknife.ButterKnife;

public class ChatActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.iv_back)ImageView ivBack;
    @BindView(R.id.tv_chat_name)TextView tvName;
    @BindView(R.id.iv_contact_message)ImageView ivContactMsg;
    @BindView(R.id.sc_chat)ScrollView scChatList;
    @BindView(R.id.ll_chat_list)LinearLayout llChatList;
    @BindView(R.id.iv_chat_keyboard)ImageView ivKeyBoard;
    @BindView(R.id.iv_chat_audio)ImageView ivAudio;
    @BindView(R.id.tv_chat_press_audio)TextView tvPressAudio;
    @BindView(R.id.iv_chat_face)ImageView ivFace;
    @BindView(R.id.iv_chat_more)ImageView ivChatMore;
    @BindView(R.id.et_chat)EditText etChat;
    @BindView(R.id.tv_send)TextView tvSend;
    private UserEntity userEntity;
    private ChatBiz chatBiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        ButterKnife.bind(this);
        userEntity = (UserEntity) getIntent().getSerializableExtra("chatData");
        initView();
        addListener();
    }
    private void initView() {
        chatBiz = new ChatBiz(userEntity.getUsername());
        if (userEntity!=null){
            tvName.setText(userEntity.getUsername());
        }
        ivKeyBoard.setOnClickListener(this);
        ivAudio.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }
    private void addListener() {
        etChat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count>0){
                    ivChatMore.setVisibility(View.GONE);
                    tvSend.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_chat_keyboard:
                //显示输入框，隐藏语音按钮
                tvPressAudio.setVisibility(View.GONE);
                etChat.setVisibility(View.VISIBLE);
                ivKeyBoard.setVisibility(View.GONE);
                ivAudio.setVisibility(View.VISIBLE);
                break;
            case R.id.iv_chat_audio:
                //显示语音按钮，隐藏输入框
                tvPressAudio.setVisibility(View.VISIBLE);
                etChat.setVisibility(View.GONE);
                ivAudio.setVisibility(View.GONE);
                ivKeyBoard.setVisibility(View.VISIBLE);
                break;
            case R.id.tv_send:
                ivChatMore.setVisibility(View.VISIBLE);
                tvSend.setVisibility(View.GONE);
                // TODO: 2017-08-16 发送消息
                chatBiz.sendTextMessage(etChat.getText().toString());
                etChat.setText("");
                break;
        }
    }
}
