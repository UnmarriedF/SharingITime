package com.fanbo.sharingitime.biz;

import com.fanbo.sharingitime.entity.UserEntity;
import com.hyphenate.chat.EMClient;

import java.util.List;

/**
 *联系人管理类
 *  Created by fanbo on 2017/6/20.
 */

public class ContactBiz {
    /**
     * 查询联系人（好友 ）列表
     * @return
     */
    public List<UserEntity> getContactList(){
        //考虑：将好友信息集成到Bmob平台
        //1.根据Bmob账号信息，在环信查询到好友列表

        //2.然后根据环信账号。查询Bnob账户信息
        return null;
    }
}
