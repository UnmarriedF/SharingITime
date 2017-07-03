package com.fanbo.sharingitime.util;

import android.text.TextUtils;

import com.fanbo.sharingitime.entity.UserEntity;

/**
 * String工具类
 * Created by fanbo on 2017/6/16.
 */

public class StringUtil {
    /**
     * 获得格式化字符串
     * @param string
     * @return  隐藏手机号的中间部分
     */
    public static String getFormatStr(String string){
        String numberHead = string.substring(0,3);
        String numberEnd = string.substring(8,11);
        StringBuilder builder = new StringBuilder("短信验证码已发送至 +86 ");
        builder.append(numberHead);
        builder.append("*****");
        builder.append(numberEnd);
        return builder.toString();
    }
    /**
     * 手机/邮箱正确性验证
     *
     * @param userEntity 用户实体类
     * @return
     */
    public static boolean isMbileOrEmail(UserEntity userEntity) {
        String str = userEntity.getUsername();
        boolean isMobile;
        boolean isEmail;
        String num = "[1][3458]\\d{9}";
        if (TextUtils.isEmpty(str)) {
            isMobile = false;
        } else {
            isMobile = str.matches(num);
        }
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            isEmail = false;
        } else {
            isEmail = str.matches(strPattern);
        }
        return isMobile || isEmail;
    }
}
