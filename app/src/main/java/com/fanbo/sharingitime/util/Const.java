package com.fanbo.sharingitime.util;

/**
 *
 * Created by fanbo on 2017/6/16.
 */

public interface Const {
    String ZERO_POINT = "00:00:00";
    String MOB_KEY = "1e64c25b79abd";
    String MOB_SECRET = "a3cfe8910c3434694a32ea1fc732e699";
    /**
     * 短信验证码发送失败
     */
    int SEND_VERIFICATION_Failed = 0;
    /**
     * 注册成功
     */
    int REGISTER_SUCCESS = 300;
    /**
     * 登陆成功
     */
    int LOGIN_SUCCESS = 301;
    /**
     * 登陆失败
     */
    int LOGIN_FALED = 302;
    /**
     * 请求好友列表成功
     */
    int GET_FRIENDS_SUCCESS = 303;
    /**
     * 请求好友列表失败
     */
    int GET_FRIENDS_FAILED = 304;
    /**
     * 注册环信密码
     */
    String HX_PASSWORD ="123456";
    /**
     * 环信获取会话列表成功
     */
    int HX_MESSAGE_LIST_SUCCESS = 306;
    /**
     * 调用手机相册
     */
    int SELECT_PHOTO_ABLUM = 200;
    /**
     * 手机拍照
     */
    int SELECT_PHOTO_CREAMER = 201;
    /**
     *裁剪图片
     */
    int PHOTO_CUT = 202;
    /**
     * 数据存储
     */
    int EXTERNAL_STORAGE = 100;
    /**
     * 软件保存根目录
     */
    String SAVE_PATH = "/sharingitime/";
    /**
     * 图片保存位置
     */
    String SAVE_IMG = "image/";

    /**
     * 新闻查询AppKey
     */
    String TITLE_NEWS_APP_KEY = "7bb021e0760723f7";
}
