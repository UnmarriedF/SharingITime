package com.fanbo.sharingitime.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.fanbo.sharingitime.R;

public class WebWiewActivity extends BaseActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_wiew);
        webView = (WebView) findViewById(R.id.wv);
        webView.loadUrl(getIntent().getStringExtra("data"));
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    protected void onDestroy() {
        webView.removeAllViews();
        webView.destroy();
        super.onDestroy();
    }
}
