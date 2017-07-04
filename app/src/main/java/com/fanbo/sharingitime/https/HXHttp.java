package com.fanbo.sharingitime.https;

import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.util.Const;
import com.fanbo.sharingitime.util.ExceptionUtil;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import java.util.List;

/**
 * 环信数据处理
 * Created by fanbo on 2017/7/2.
 */

public class HXHttp {
    /**
     * 创建环信聊天账号
     * @param userEntity
     */
    public void creatCount(final UserEntity userEntity){
        new  Thread(){
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(userEntity.getUsername(), Const.HX_PASSWORD);
                } catch (HyphenateException e) {
                    ExceptionUtil.handleException(e);
                }
            }
        }.start();
    }

    /**
     * 登陆到环信账号
     * @param userEntity
     */
    public void loginHX(final UserEntity userEntity){
        new  Thread(){
            @Override
            public void run() {
                EMClient.getInstance().login(userEntity.getUsername(), "123456", new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        // TODO: 2017/6/21  登陆聊天服务器成功(环信账号的创建与登陆处理线程安全问题)
                        EMClient.getInstance().chatManager().loadAllConversations();
                        EMClient.getInstance().groupManager().loadAllGroups();
                    }
                    @Override
                    public void onError(int code, String error) {
                        // TODO: 2017/6/21  登陆聊天服务器失败
                    }

                    @Override
                    public void onProgress(int progress, String status) {

                    }
                });
            }
        }.start();
    }

    public List<String> getFriendList(){
        new Thread(){
            @Override
            public void run() {
                try {
                    List<String> friends = EMClient.getInstance().contactManager().getAllContactsFromServer();
                } catch (HyphenateException e) {
                    ExceptionUtil.handleException(e);
                }
            }
        }.start();
        return null;
    }
}
