package com.fanbo.sharingitime.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.fanbo.sharingitime.activity.MyApplication;

/**
 * Toast管理类
 * Created by fanbo on 2017/6/24.
 */

public class ToastUtil {
    private static Toast TOAST;
    private static final String TAG = "ToastUtil";

    public static void show(String str){
        ToastUtil.show(MyApplication.applicationContext,str);
    }
    //短时间吐司
    public static void show(Context context, String str) {
        show(context, str, Toast.LENGTH_SHORT);
    }
    /**
     * 自定义吐司事件
     * @param context 上下文
     * @param text 吐司文本
     * @param duration 吐司时间
     */
    public static void show(@NonNull final Context context, @NonNull final String text, final int duration) {

        if (TOAST == null) {
            TOAST = Toast.makeText(context, text, duration);
        } else {
            TOAST.setText(text);
            TOAST.setDuration(duration);
        }

        TOAST.show();
    }
}
