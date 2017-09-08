package com.fanbo.sharingitime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.fanbo.sharingitime.R;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!MyApplication.isFirstLogin) {
                    startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
                } else {
                    startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);
        ImageView iv = (ImageView) findViewById(R.id.iv_welcome);
        Glide.with(this).load(R.drawable.sea).into(iv);
    }
}
