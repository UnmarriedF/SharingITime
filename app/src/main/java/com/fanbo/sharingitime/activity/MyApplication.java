package com.fanbo.sharingitime.activity;

import android.app.Application;
import android.content.Context;

import com.fanbo.sharingitime.db.SharePreferencesUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;

import cn.bmob.v3.Bmob;


/**
 * Created by fanbo on 2017/6/11.
 */

public class MyApplication extends Application {
    public static Context applicationContext;
    /**
     * 版本是否发布标识
     */
    public static boolean isRelease = false;
    /**
     * 首次登陆标识
     */
    public static boolean isFirstLogin = false;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        //获取首次登陆状态
        SharePreferencesUtil sp = new SharePreferencesUtil();
        // TODO: 2017/6/22  更改为从偏好获取
        isFirstLogin = (sp.getUser().getUsername()!=null);
        //初始化Bmob
        Bmob.initialize(this, "d3fcaeec29070d4fd74d776775c72bdb");
        //初始化环信
        EMClient.getInstance().init(MyApplication.applicationContext,new EMOptions());
    }
}
