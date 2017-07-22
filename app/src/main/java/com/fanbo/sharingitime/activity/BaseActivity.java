package com.fanbo.sharingitime.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.util.Const;

import static com.fanbo.sharingitime.util.Const.EXTERNAL_STORAGE;

public abstract class BaseActivity extends AppCompatActivity {

    public RelativeLayout titlebar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
    }

    /**
     * 获取数据存储的权限
     */
    public void checkStorage() {
        //如果已经授权，则存储数据，没有授权，请求授权
        if (ContextCompat.checkSelfPermission(MyApplication.applicationContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA,Manifest.permission.READ_PHONE_STATE}, EXTERNAL_STORAGE);
            }
        }
    }

    /**
     * 获取相机权限组的数据
     */
    public void checkCrmera() {
        //如果已经授权，则存储数据，没有授权，请求授权
        if (ContextCompat.checkSelfPermission(MyApplication.applicationContext,
                Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, Const.SELECT_PHOTO_CREAMER);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    }

    /**
     * 初始化头布局
     * @param leftImg
     * @param leftStr
     * @param midStr
     * @param rightStr
     * @param rightImg
     */
    public void initTitleBar(int leftImg,String leftStr,String midStr,String rightStr,int rightImg){
        ImageView ivLeft = (ImageView) titlebar.findViewById(R.id.iv_title_left);
        ImageView ivRight = (ImageView) titlebar.findViewById(R.id.iv_title_right);
        TextView tvLeft = (TextView) titlebar.findViewById(R.id.tv_title_left);
        TextView tvRight = (TextView) titlebar.findViewById(R.id.tv_title_right);
        TextView tvMid = (TextView) titlebar.findViewById(R.id.tv_title_mid);
        if (leftImg!=0){
            ivLeft.setVisibility(View.VISIBLE);
            ivLeft.setImageResource(leftImg);
        }
        if (rightImg!=0){
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageResource(rightImg);
        }
        if (!TextUtils.isEmpty(leftStr)){
            tvLeft.setVisibility(View.VISIBLE);
            tvLeft.setText(leftStr);
        }
        if (!TextUtils.isEmpty(rightStr)){
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(rightStr);
        }
        if (!TextUtils.isEmpty(midStr)){
            tvMid.setVisibility(View.VISIBLE);
            tvMid.setText(midStr);
        }
    }
}
