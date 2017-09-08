package com.fanbo.sharingitime.biz;

import com.fanbo.sharingitime.entity.UserEntity;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMMessage;

/**
 * 聊天业务逻辑
 * Created by fanbo on 2017-08-19.
 */

public class ChatBiz {
    private String toUsername;

    public ChatBiz(String toUsername) {
        this.toUsername = toUsername;
    }

    /**
     * 发送文本消息
     */
    public void sendTextMessage(String text){
        EMMessage message = EMMessage.createTxtSendMessage(text,toUsername);
        EMClient.getInstance().chatManager().sendMessage(message);
    }
}
