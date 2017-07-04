package com.fanbo.sharingitime.https;

import android.net.Uri;
import android.text.TextUtils;

import com.fanbo.sharingitime.db.SharePreferencesUtil;
import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.util.ExceptionUtil;

import java.io.File;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

/**
 * Bmob文件保存
 * Created by fanbo on 2017/6/30.
 */

public class BmobHttp {
    /**
     * 更新用户信息到Bmob后台
     * @param userEntity
     */
    public static void updateUserToBmob(UserEntity userEntity) {
        BmobHttp.setUserHeaderImg(userEntity);
    }

    /**
     * 保存账户信息到本地，从本地获取头像信息，如果为空，则不设置
     * @param userEntity
     * @return
     */
    public static void setUserHeaderImg(final UserEntity userEntity){
        // TODO: 2017/6/30 进行头像相同验证，如果相同，则不进行头像更新
        SharePreferencesUtil sp = new SharePreferencesUtil();
        sp.saveData("username",userEntity.getUsername());
        String headerImg = sp.getData("headerImg");
        if (!TextUtils.isEmpty(headerImg)) {
            BmobHttp.saveFile(headerImg, new OnBmobFileLoadListener() {
                @Override
                public void onFileLoadEnd(String fileUrl, BmobFile file) {
                    userEntity.setHeaderPath(fileUrl);
                    userEntity.setHeaderImg(file);
                    userEntity.update(userEntity.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e==null){
                                //更新成功
                            }else {
                                //更新失败
                                ExceptionUtil.handleException(e);
                            }
                        }
                    });
                }

            });
        }
    }

    public static void saveFile(String path, final OnBmobFileLoadListener loadListener){
        Uri uri = Uri.parse(path);
        String filePath = uri.getPath();
        final BmobFile file = new BmobFile(new File(filePath));
        file.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e==null){
                loadListener.onFileLoadEnd(file.getFileUrl(),file);
                }else {
                    ExceptionUtil.handleException(e);
                }
            }
        });
    }

    /**
     * Bmob文件传输监听接口
     */
    public interface OnBmobFileLoadListener{
        void onFileLoadEnd(String fileUrl,BmobFile file);
    }
    /**
     * Bmob文件查询监听接口
     */
    public interface OnBmobObjectQueryListener{
        void onQueryEnd(BmobObject bmobObject);
    }
    /**
     * Bmob查询单个文件
     */
    public static void getSingleBmobObject(String objectId, final OnBmobObjectQueryListener onBmobObjectQueryListener){
        BmobQuery<UserEntity> query = new BmobQuery<>();
        query.getObject("objectId", new QueryListener<UserEntity>() {
            @Override
            public void done(UserEntity userEntity, BmobException e) {
                if (e==null){
                    onBmobObjectQueryListener.onQueryEnd(userEntity);
                }else {
                    ExceptionUtil.handleException(e);
                }
            }
        });
    }
}
