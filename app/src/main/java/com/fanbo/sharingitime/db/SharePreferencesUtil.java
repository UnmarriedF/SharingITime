package com.fanbo.sharingitime.db;

import android.content.Context;
import android.content.SharedPreferences;

import com.fanbo.sharingitime.activity.MyApplication;
import com.fanbo.sharingitime.entity.UserEntity;

/**
 * 偏好设置数据存储类
 * Created by fanbo on 2017/6/19.
 */

public class SharePreferencesUtil {
    private SharedPreferences.Editor editor;
    private SharedPreferences sp;

    public SharePreferencesUtil() {
        sp = MyApplication.applicationContext.getSharedPreferences("user", Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    public void saveData(String key,String value){
        editor.putString(key,value);
        editor.commit();
    }
    public String getData(String key){
        return sp.getString(key,"");
    }

    public void saveUser(UserEntity userEntity) {
        editor.putString("username", userEntity.getUsername());
        editor.commit();
    }

    public UserEntity getUser() {
        String username = sp.getString("username", "");
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(username);
        return userEntity;
    }
    // TODO: 2017/6/19  在软件卸载时，清除偏好设置
}
