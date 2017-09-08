package com.fanbo.sharingitime.biz;


import android.os.Handler;
import android.os.Message;

import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.https.BmobHttp;
import com.fanbo.sharingitime.https.HXHttp;
import com.fanbo.sharingitime.util.Const;

import java.util.List;

/**
 * 联系人管理类
 * Created by fanbo on 2017/6/20.
 */

public class ContactBiz implements BmobHttp.OnBmobFriendsQueryListener {
    private Handler mHandler;

    public ContactBiz(Handler handler) {
        mHandler = handler;
    }

    /**
     * 查询联系人（好友 ）列表
     */
    public void getContactList() {
        // TODO: 2017-08-15 更新联系人列表太慢了，需要更改请求方式
        //考虑：将好友信息集成到Bmob平台
        //根据Bmob账号信息，在环信查询到好友列表
        new HXHttp().getFriendList(new HXHttp.GetHXFriendListListener() {
            @Override
            public void onGetHXFriedns(List<String> list) {
                if (list==null){
                    mHandler.sendEmptyMessage(Const.GET_FRIENDS_FAILED);
                } else if (list.size()>0){
                    //向Bmob发起请求，请求联系人的详细数据
                    BmobHttp.getFriendsList(list,ContactBiz.this);
                }
            }
        });
    }

    /**
     * Bmob查询联系人列表监听
     * @param list 查询完毕的联系人详细信息列表
     */
    @Override
    public void onFriendListLoadEnd(List<UserEntity> list) {
        if (list.size()>0){
            //查询成功，向Activity发消息，传递列表
            Message message = mHandler.obtainMessage();
            message.obj = list;
            message.what = Const.GET_FRIENDS_SUCCESS;
            mHandler.sendMessage(message);
        }else {
            //查询失败
            mHandler.sendEmptyMessage(Const.GET_FRIENDS_FAILED);
        }
    }
}
