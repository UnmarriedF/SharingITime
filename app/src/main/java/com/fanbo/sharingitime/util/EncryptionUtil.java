package com.fanbo.sharingitime.util;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 安全加密工具
 * Created by fanbo on 2017/6/16.
 */

public class EncryptionUtil {
    /**
     * 字符串加密
     * @param data 加密数据
     * @return MD5格式密码
     */
    public static String getStrFromStr(String data) {
        if (TextUtils.isEmpty(data)){
            return "";
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(data.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuffer buffer = new StringBuffer();
            for (byte b:bytes){
                //转换成16进制
                String temp = Integer.toHexString(b&0xff);
                if (temp.length()==1){
                    buffer.append("0").append(temp);
                }else {
                    buffer.append(temp);
                }
            }
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            ExceptionUtil.handleException(e);
            return "";
        }
    }
}
