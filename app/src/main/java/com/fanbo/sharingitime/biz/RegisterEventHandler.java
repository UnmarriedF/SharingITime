package com.fanbo.sharingitime.biz;

import android.os.Handler;
import android.os.Message;

import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.util.Const;
import com.fanbo.sharingitime.util.ExceptionUtil;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by fanbo on 2017/6/16.
 */

public class RegisterEventHandler extends EventHandler {
    private Handler mHandler;
    private UserEntity mUser;

    public RegisterEventHandler(Handler handler, UserEntity userEntity) {
        mHandler = handler;
        mUser = userEntity;
    }

    @Override
    public void afterEvent(int event, int result, Object data) {
        Message message = mHandler.obtainMessage();
        if (result == SMSSDK.RESULT_COMPLETE) {
            message.what = event;
        } else {
            message.what = Const.SEND_VERIFICATION_Failed;
            ExceptionUtil.handleException((Throwable) data);
        }
        message.obj = mUser;
        mHandler.sendMessage(message);
    }
}
