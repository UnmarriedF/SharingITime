package com.fanbo.sharingitime.entity;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户实体类
 * Created by fanbo on 2017/6/16.
 */

public class UserEntity extends BmobUser {
    /**
     * 临时使用
     */
    private BmobFile headerImg;
    /**
     * 头像路径
     */
    private String headerPath;
    /**
     * 个性化姓名
     */
    private String pickName;
    public String getPickName() {
        return pickName;
    }

    public void setPickName(String pickName) {
        this.pickName = pickName;
    }


    public String getHeaderPath() {
        return headerPath;
    }

    public void setHeaderPath(String headerPath) {
        this.headerPath = headerPath;
    }

    public BmobFile getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(BmobFile headerImg) {
        this.headerImg = headerImg;
    }
}
