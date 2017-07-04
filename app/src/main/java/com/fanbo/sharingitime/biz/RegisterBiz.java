package com.fanbo.sharingitime.biz;


import android.os.Handler;


import com.fanbo.sharingitime.activity.MyApplication;
import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.https.BmobHttp;
import com.fanbo.sharingitime.https.HXHttp;
import com.fanbo.sharingitime.util.Const;
import com.fanbo.sharingitime.util.ExceptionUtil;
import com.fanbo.sharingitime.util.ToastUtil;
import com.mob.MobSDK;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * 注册业务逻辑
 * Created by fanbo on 2017/6/15.
 */

public class RegisterBiz {
    private EventHandler eventHandler;
    private Handler mHandler;

    public RegisterBiz(Handler handler) {
        mHandler = handler;
    }

    public void sendVerification(final UserEntity userEntity) {
        MobSDK.init(MyApplication.applicationContext, Const.MOB_KEY, Const.MOB_SECRET);
        String number = userEntity.getUsername();
        SMSSDK.getVerificationCode("86", number);
        eventHandler = new RegisterEventHandler(mHandler, userEntity);
        // TODO: 2017/6/16  注册监听回调，在注册完成时解除注册监听
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 提交验证码
     *
     * @param userEntity
     * @param code
     */
    public void submitVerification(UserEntity userEntity, String code) {
        MobSDK.init(MyApplication.applicationContext, Const.MOB_KEY, Const.MOB_SECRET);
        SMSSDK.submitVerificationCode("86", userEntity.getUsername(), code);
        eventHandler = new RegisterEventHandler(mHandler, userEntity);
        SMSSDK.registerEventHandler(eventHandler);
    }

    /**
     * 注销短信监听事件
     */
    public void unregisterEventHandler() {
        if (eventHandler != null) {
            SMSSDK.unregisterEventHandler(eventHandler);
        }
        eventHandler = null;
    }

    /**
     * 保存用户到Bmob、环信、偏好设置
     *
     * @param userEntity
     */
    public void saveUser(final UserEntity userEntity) {
        this.unregisterEventHandler();
        // TODO: 2017/6/30  保存头像
        //BmobHttp.setUserHeaderImg(userEntity).
        userEntity.signUp(new SaveListener<UserEntity>() {
            @Override
            public void done(UserEntity userEntity, BmobException e) {
                if (e == null) {
                    //账号注册成功
                    mHandler.sendEmptyMessage(Const.REGISTER_SUCCESS);
                    //创建聊天账号（环信）
                    new HXHttp().creatCount(userEntity);
                } else {
                    // TODO: 2017/6/22 bmob注册失败
                    ExceptionUtil.handleException(e);
                    ToastUtil.show(MyApplication.applicationContext, "注册失败");
                }
            }
        });
    }

    /**
     * 登陆
     *
     * @param userEntity
     */
    public void login(UserEntity userEntity) {
        userEntity.login(new SaveListener<UserEntity>() {
            @Override
            public void done(UserEntity userEntity, BmobException e) {
                if (e == null) {
                    //登陆成功，更新头像
                    BmobHttp.updateUserToBmob(userEntity);
                    mHandler.sendEmptyMessage(Const.LOGIN_SUCCESS);
                    //登陆聊天账号
                    new HXHttp().loginHX(userEntity);
                } else {
                    // TODO: 2017/6/22 bmob账号登陆失败
                    if (e.getErrorCode() == 202) {
                        ToastUtil.show("账号已存在.请重新设置账号");
                    } else {
                        mHandler.sendEmptyMessage(Const.LOGIN_FALED);
                        ToastUtil.show("登陆失败.请重试");
                        ExceptionUtil.handleException(e);
                    }
                }
            }
        });
    }

}
