package com.fanbo.sharingitime.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.fanbo.sharingitime.activity.MyApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 文件管理类
 * Created by fanbo on 2017/6/23.
 */

public class FileUtil {
    /**
     * 判断sdcard是否可用
     *
     * @return
     */
    public static boolean sdcardExist() {
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        return false;
    }

    /**
     * 获取文件保存的目录
     *
     * @param folder 将要保存的文件路径
     * @param fileName 文件名
     * @return 保存的文件夹
     */
    public static File getSaveFile(String folder,String fileName) {
        String rootPath = Environment.getExternalStorageDirectory() + Const.SAVE_PATH;
        rootPath = rootPath+folder;
        File localkFile = new File(rootPath);
        if (!localkFile.exists()) {
            localkFile.mkdir();
        }
        File savePath = new File(localkFile, fileName);
        //文件覆盖
        if (savePath.exists()) {
            savePath.delete();
        }
        return savePath;
    }

    /***
     * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的
     * 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
     * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
     */
    public static Uri getPhotoUri() {
        ContentValues contentValues = new ContentValues();
        ContentResolver resolver = MyApplication.applicationContext.getContentResolver();
        Uri photoUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        return photoUri;

    }
}
