package com.fanbo.sharingitime.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.util.Const;

import static com.fanbo.sharingitime.util.Const.EXTERNAL_STORAGE;

public abstract class BaseActivity extends AppCompatActivity {

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
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA}, EXTERNAL_STORAGE);
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
}
