package com.fanbo.sharingitime.activity.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.RegisterActivity;
import com.fanbo.sharingitime.biz.RegisterBiz;
import com.fanbo.sharingitime.entity.UserEntity;
import com.fanbo.sharingitime.util.Const;
import com.fanbo.sharingitime.util.EncryptionUtil;
import com.fanbo.sharingitime.util.StringUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterInputFragment extends BaseFragment {


    private Button btnNextStep;
    private EditText etMobileEmail;
    private TextView tvHint;
    private ImageView ivCancel;
    private RegisterActivity registerActivity;
    private Handler registerInputHandler;
    private RegisterBiz registerBiz;
    private boolean isLogin;
    private UserEntity userEntity;
    private TextView tvTitle;

    public RegisterInputFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle!=null) {
            isLogin = bundle.getBoolean("isLogin");
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_register_input, container, false);
        initView();
        addListener();
        return contentView;
    }

    private void addListener() {
        btnNextStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnNextStep.setText(R.string.next_step);
                btnNextStep.setClickable(false);
                String str = etMobileEmail.getText().toString();
                userEntity = new UserEntity();
                userEntity.setUsername(str);
                boolean isCorrect = StringUtil.isMbileOrEmail(userEntity);
                if (isCorrect) {
                    // TODO: 2017/6/18  恢复 跳过短信验证 发送验证码  进度条显示，等待验证码发送成功
                    registerBiz.sendVerification(userEntity);
                } else {
                    tvHint.setText(R.string.error_mobile_or_email);
                    btnNextStep.setClickable(true);
                }

            }
        });
        etMobileEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {
                //输入之前
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                //输入时
                if (count > 0) {
                    tvHint.setText(R.string.input_mobile_number_or_email);
                    tvHint.setVisibility(View.VISIBLE);
                    ivCancel.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //在输入完成之后
                if (editable.toString().length() == 0) {
                    tvHint.setVisibility(View.INVISIBLE);
                    ivCancel.setVisibility(View.INVISIBLE);
                }

            }
        });
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                etMobileEmail.setText("");
                tvHint.setVisibility(View.INVISIBLE);
                ivCancel.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void initView() {
        registerInputHandler = new RegisterInputHandler();
        registerActivity = (RegisterActivity) getActivity();
        btnNextStep = (Button) contentView.findViewById(R.id.btn_register);
        etMobileEmail = (EditText) contentView.findViewById(R.id.et_mobile_number_Email);
        tvHint = (TextView) contentView.findViewById(R.id.tv_edit_hint);
        ivCancel = (ImageView) contentView.findViewById(R.id.iv_cancel);
        tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        registerBiz = new RegisterBiz(registerInputHandler);
        if (isLogin){
            tvTitle.setText("获取验证码");
        }
    }

    class RegisterInputHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Const.SEND_VERIFICATION_Failed:
                    tvHint.setText("验证码发送失败");
                    btnNextStep.setClickable(true);
                    btnNextStep.setText(R.string.regain);
                    break;
                default:
                    Message message = new Message();
                    message.what = msg.what;
                    message.obj = msg.obj;
                    registerBiz.unregisterEventHandler();
                    registerActivity.handler.sendMessage(message);
            }
        }
    }

    @Override
    public void onDestroy() {
        registerBiz = null;
        registerActivity = null;
        super.onDestroy();
    }
}
