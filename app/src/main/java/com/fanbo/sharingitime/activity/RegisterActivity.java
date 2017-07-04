package com.fanbo.sharingitime.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.fragment.RegisterInputFragment;
import com.fanbo.sharingitime.activity.fragment.VerificationInputFragment;
import com.fanbo.sharingitime.db.SharePreferencesUtil;
import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.util.Const;
import com.fanbo.sharingitime.util.DateUtil;
import com.fanbo.sharingitime.util.FileUtil;
import com.fanbo.sharingitime.util.ToastUtil;
import com.fanbo.sharingitime.widget.CircleImageView;
import com.fanbo.sharingitime.widget.PhotoPopWindow;

import cn.smssdk.SMSSDK;

public class RegisterActivity extends BaseActivity {
    private Fragment[] fragments = new Fragment[2];
    public Handler handler;
    private FragmentManager fm;
    private ImageView ivBack;
    private CircleImageView civ;
    private boolean isLogin;
    private PhotoPopWindow popWindow;
    private Uri cameraUri;
    private Uri showHeaderUri;
    private Uri headerUri;
    private UserEntity userEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        addListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        popWindow.dismiss();
    }

    private void addListener() {
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fm.popBackStack();
            }
        });
        civ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 2017/6/22 从本地选择照片保存为头像
                if (popWindow != null) {
                    if (!popWindow.isShowing()) {
                        popWindow.showAtLocation(findViewById(R.id.activity_register), Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                    } else {
                        popWindow.dismiss();
                    }
                }
            }
        });
    }

    private void initView() {
        fm = getSupportFragmentManager();
        fragments[0] = new RegisterInputFragment();
        fragments[1] = new VerificationInputFragment();
        showFragment(1, null, true);
        handler = new RegisterHandler();
        ivBack = (ImageView) findViewById(R.id.iv_back);
        civ = (CircleImageView) findViewById(R.id.civ_head);
        SharePreferencesUtil sp = new SharePreferencesUtil();
        showHeaderUri = Uri.parse(sp.getData("headerImg"));
        // TODO: 2017/6/19  点击设置头像，在头像加载完成前，进度条
        // 1.在账号退出之后再次登录的，显示默认图片
        // 2.获取本地的头像，本地没有头像加载一个默认的头像
        if (TextUtils.isEmpty(showHeaderUri.toString())) {
            Glide.with(this).load(R.drawable.little_huang).into(civ);
        }else {
            Glide.with(this).load(showHeaderUri).into(civ);
        }
        popWindow = new PhotoPopWindow(this, handler);
    }

    class RegisterHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                    //获取验证码成功
                    userEntity = (UserEntity) msg.obj;
                    onBackPressed();
                    updateVerificationView(userEntity);
                    break;
                case Const.SELECT_PHOTO_CREAMER:
                    //从相机获取图片
                    //判断存储卡是否可以使用
                    if (FileUtil.sdcardExist()) {
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        cameraUri = FileUtil.getPhotoUri();
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, cameraUri);
                        startActivityForResult(intent, Const.SELECT_PHOTO_CREAMER);
                    } else {
                        ToastUtil.show(RegisterActivity.this, "存储卡不可用");
                    }
                    break;
                case Const.SELECT_PHOTO_ABLUM:
                    //从相册获取到图片
                    if (FileUtil.sdcardExist()) {
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        startActivityForResult(intent, Const.SELECT_PHOTO_ABLUM);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            switch (requestCode) {
                case Const.SELECT_PHOTO_ABLUM:
                    cutPicture(Uri.parse(data.getData().toString()));
                    break;
                case Const.SELECT_PHOTO_CREAMER:
                    cutPicture(cameraUri);
                    break;
                case Const.PHOTO_CUT:
                    // TODO: 2017/6/25 设置头像，并将头像资源保存在网络,裁剪图片,保存图片到本地
                    //uri相同，要清除glide的缓存
                    Glide.with(this).load(headerUri).into(civ);
                    break;
            }
        super.onActivityResult(requestCode, requestCode, data);
    }

    /**
     * 剪切图片
     */
    private void cutPicture(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        //圆形裁剪
//        //设置裁剪
//        intent.putExtra("crop", true);
//        //设置宽高比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        //裁剪图片的宽高
//        intent.putExtra("outputX", 300);
//        intent.putExtra("outputY", 300);
//        intent.putExtra("return-data", true);
        //自由裁剪
        intent.putExtra("scale", true);
        String imgName = DateUtil.getDateTag()+"header.jpg";
        headerUri = Uri.fromFile(FileUtil.getSaveFile(Const.SAVE_IMG,imgName));
        SharePreferencesUtil sp = new SharePreferencesUtil();
        sp.saveData("headerImg",headerUri.toString());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, headerUri);
        startActivityForResult(intent, Const.PHOTO_CUT);
    }

    private void updateVerificationView(UserEntity userEntity) {
        ((VerificationInputFragment) fragments[1]).updateView(userEntity);
    }

    public void showFragment(int index, UserEntity userEntity, boolean isLogin) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", userEntity);
        bundle.putBoolean("isLogin", isLogin);
        fragments[index].setArguments(bundle);
        transaction.replace(R.id.fl_fragment_container, fragments[index]);
        if (index == 1) {
            transaction.commit();
            return;
        }
        transaction.addToBackStack("verification");
        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        fm.popBackStack("verification", 0);
    }

    @Override
    protected void onDestroy() {
        popWindow.dismiss();
        super.onDestroy();
    }
}
