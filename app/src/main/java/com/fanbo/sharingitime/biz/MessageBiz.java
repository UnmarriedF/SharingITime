package com.fanbo.sharingitime.biz;

import android.os.Handler;
import android.os.Message;

import com.fanbo.sharingitime.https.HXMessage;
import com.fanbo.sharingitime.util.Const;

import java.util.Map;

/**
 * 消息列表界面业务逻辑
 * Created by fanbo on 2017-08-15.
 */

public class MessageBiz {
    private HXMessage hxMessage;
    private Handler messageHandler;
    public MessageBiz(Handler handler) {
        messageHandler = handler;
        hxMessage = new HXMessage();
    }

    // TODO: 2017-08-15 获取所有的会话信息，暂时返回null
    public void getChatList(){
        Message message = messageHandler.obtainMessage();
        message.what = Const.HX_MESSAGE_LIST_SUCCESS;
        message.obj = null;
        messageHandler.sendMessage(message);
    }
}
