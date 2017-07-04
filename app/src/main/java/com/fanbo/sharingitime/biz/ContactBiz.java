package com.fanbo.sharingitime.biz;

import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.https.BmobHttp;
import com.fanbo.sharingitime.https.HXHttp;
import com.hyphenate.chat.EMClient;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobUser;

/**
 *联系人管理类
 *  Created by fanbo on 2017/6/20.
 */

public class ContactBiz{
    /**
     * 查询联系人（好友 ）列表
     * @return
     */
    public int getContactList(BmobHttp.OnBmobObjectQueryListener onBmobObjectQueryListener){
        //考虑：将好友信息集成到Bmob平台
        //1.根据Bmob账号信息，在环信查询到好友列表
        List<String> friendList = new HXHttp().getFriendList();
        //2.然后根据环信账号。查询Bmob账户信息
        for (int i = 0; i < friendList.size(); i++) {
            BmobHttp.getSingleBmobObject(friendList.get(i),onBmobObjectQueryListener);
        }
        return 0;
    }
}
