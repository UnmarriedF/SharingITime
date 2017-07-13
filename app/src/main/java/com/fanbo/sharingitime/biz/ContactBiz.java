package com.fanbo.sharingitime.biz;


import android.os.Handler;
import android.os.Message;

import com.fanbo.sharingitime.https.HXHttp;
import com.fanbo.sharingitime.util.Const;

import java.util.List;

/**
 * 联系人管理类
 * Created by fanbo on 2017/6/20.
 */

public class ContactBiz {
    private Handler mHandler;

    public ContactBiz(Handler handler) {
        mHandler = handler;
    }

    /**
     * 查询联系人（好友 ）列表
     */
    public void getContactList() {
        //考虑：将好友信息集成到Bmob平台
        //根据Bmob账号信息，在环信查询到好友列表
        new HXHttp().getFriendList(new HXHttp.GetHXFriendListListener() {
            @Override
            public void onGetHXFriedns(List<String> list) {
                if (list.size()>0){
                    Message message = mHandler.obtainMessage();
                    message.obj = list;
                    message.what = Const.GET_FRIENDS_SUCCESS;
                    mHandler.sendMessage(message);
                }else {
                    mHandler.sendEmptyMessage(Const.GET_FRIENDS_FAILED);
                }
            }
        });
    }
}
