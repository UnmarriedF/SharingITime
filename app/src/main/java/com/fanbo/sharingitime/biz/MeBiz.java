package com.fanbo.sharingitime.biz;

import com.fanbo.sharingitime.db.SharePreferencesUtil;
import com.fanbo.sharingitime.entity.UserEntity;

/**
 * Created by fanbo on 2017-07-23.
 */

public class MeBiz {
    public UserEntity getUser(){
        SharePreferencesUtil sp= new SharePreferencesUtil();
        UserEntity userEntity = new UserEntity();
        userEntity.setHeaderPath(sp.getData("headerImg"));
        userEntity.setUsername(sp.getData("username"));
        return userEntity;
    }
}
