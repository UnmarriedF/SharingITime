package com.fanbo.sharingitime.activity.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.MainActivity;
import com.fanbo.sharingitime.activity.MyApplication;
import com.fanbo.sharingitime.activity.RegisterActivity;
import com.fanbo.sharingitime.biz.RegisterBiz;
import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.util.Const;
import com.fanbo.sharingitime.util.EncryptionUtil;
import com.fanbo.sharingitime.util.StringUtil;


import cn.smssdk.SMSSDK;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerificationInputFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvHint;
    private EditText etVerification;
    private EditText etPassword;
    private TextView tvRegain;
    private Handler handler;
    private int countDown = 60;
    private ImageView ivPwdHide;
    private UserEntity user;
    private RegisterBiz registerBiz;
    private boolean isHidden = true;
    private Button btnRegister;
    private String formatNumber;
    private String number;
    private boolean isLogin;
    private TextView tvMobileLogin;
    private TextView tvForgetPwd;
    private String password;
    private RegisterActivity registerActivity;

    public VerificationInputFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            user = (UserEntity) bundle.getSerializable("user");
            isLogin = bundle.getBoolean("isLogin");
            if (!isLogin) {
                number = user.getUsername();
                formatNumber = StringUtil.getFormatStr(number);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_verification_input, container, false);
        initView();
        addListener();
        return contentView;
    }

    private void addListener() {
        etPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int after, int count) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int after, int count) {
                if (count > 0) {
                    ivPwdHide.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.toString().length() == 0) {
                    ivPwdHide.setVisibility(View.GONE);
                }
            }
        });
        ivPwdHide.setOnClickListener(this);
        btnRegister.setOnClickListener(this);
        tvRegain.setOnClickListener(this);
        tvHint.setOnClickListener(this);
        tvMobileLogin.setOnClickListener(this);
        tvForgetPwd.setOnClickListener(this);
    }

    private void initView() {
        registerActivity = (RegisterActivity) getActivity();
        etVerification = (EditText) contentView.findViewById(R.id.et_mobile_verification);
        etPassword = (EditText) contentView.findViewById(R.id.et_password);
        ivPwdHide = (ImageView) contentView.findViewById(R.id.iv_password_hide);
        btnRegister = (Button) contentView.findViewById(R.id.btn_register);
        tvRegain = (TextView) contentView.findViewById(R.id.tv_resend_verification);
        tvHint = (TextView) contentView.findViewById(R.id.tv_hint);
        handler = new VerificationHandler();
        registerBiz = new RegisterBiz(handler);
        etPassword.setText("");
        if (isLogin) {
            tvMobileLogin = (TextView) contentView.findViewById(R.id.tv_mobile_login);
            tvForgetPwd = (TextView) contentView.findViewById(R.id.tv_forget_password);
            tvHint.setText("新用户？注册");
            tvHint.setClickable(true);
            tvHint.setTextColor(Color.WHITE);
            btnRegister.setText("登录");
            etVerification.setHint("请输入账号");
            etPassword.setHint("请输入密码");
            tvMobileLogin.setVisibility(View.VISIBLE);
            tvForgetPwd.setVisibility(View.VISIBLE);
            tvRegain.setVisibility(View.GONE);
        } else {
            updateView(user);
        }

    }

    public void updateView(UserEntity userEntity) {
        isLogin = false;
        btnRegister.setText("注册");
        user = userEntity;
        tvHint.setClickable(false);
        etVerification.setHint("请输入验证码");
        tvHint.setVisibility(View.VISIBLE);
        tvHint.setTextColor(Color.BLACK);
        tvHint.setText(formatNumber);
        handler.postDelayed(new TimeRunnable(), 500);
        tvRegain.setClickable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_resend_verification:
                tvRegain.setClickable(false);
                btnRegister.setClickable(true);
                registerBiz.sendVerification(user);
                handler.postDelayed(new TimeRunnable(), 500);
                break;
            case R.id.btn_register:
                // TODO: 2017/6/30 在注册前，判断账号是否存在，并提示
                if (user == null) {
                    user = new UserEntity();
                }
                btnRegister.setClickable(false);
                password = etPassword.getText().toString();
                String encryptionStr = EncryptionUtil.getStrFromStr(password);
                String code = etVerification.getText().toString();
                user.setPassword(encryptionStr);
                if (isLogin) {
                    user.setUsername(code);
                    registerBiz.login(user);
                } else {
                    //提交验证码
                    registerBiz.submitVerification(user, code);
                }
                break;
            case R.id.iv_password_hide:
                if (isHidden) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isHidden = false;
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isHidden = true;
                }
                break;
            case R.id.tv_mobile_login:
                registerActivity.showFragment(0, null, true);
                break;
            case R.id.tv_hint:
                registerActivity.showFragment(0, null, false);
                break;
        }
    }

    class VerificationHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SMSSDK.EVENT_GET_VERIFICATION_CODE:
                    handler.postDelayed(new TimeRunnable(), 1000);
                    break;
                case Const.REGISTER_SUCCESS:
                    break;
                case Const.LOGIN_SUCCESS:
                    startActivity(new Intent(MyApplication.applicationContext, MainActivity.class));
                    //关闭当前activity
                    registerActivity.finish();
                    break;
                case Const.LOGIN_FALED:
                    btnRegister.setClickable(true);
                    break;
                case SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE:
                    //则保存注册信息到Bmob后台
                    registerBiz.saveUser(user);
                    break;
            }
        }
    }

    class TimeRunnable implements Runnable {

        @Override
        public void run() {
            if (countDown > 0) {
                tvRegain.setVisibility(View.VISIBLE);
                countDown--;
                tvRegain.setText(countDown + "秒后重新发送");
                handler.postDelayed(this, 1000);
            } else if (countDown == 0) {
                countDown = 60;
                tvRegain.setText("重新发送");
                tvRegain.setClickable(true);
            }
        }
    }

    @Override
    public void onDestroy() {
        registerActivity = null;
        registerBiz = null;
        countDown = 0;
        super.onDestroy();
    }
}
