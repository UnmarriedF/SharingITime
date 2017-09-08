package com.fanbo.sharingitime.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.fragment.MeFragment;
import com.fanbo.sharingitime.activity.fragment.MessageFragment;
import com.fanbo.sharingitime.activity.fragment.ShareFragment;
import com.fanbo.sharingitime.activity.fragment.ToolsFragment;
import com.fanbo.sharingitime.service.HXMessageListenerService;

public class MainActivity extends BaseActivity {

    private Fragment[] fragments = new Fragment[4];
    private RadioGroup rg;
    private int currentIndex = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        addListener();
    }

    private void addListener() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int index = rg.indexOfChild(rg.findViewById(i));
                showFragment(index);
            }
        });
    }

    private void showFragment(int showIndex) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        if (currentIndex!=showIndex){
            transaction.hide(fragments[currentIndex]);
        }
        if (fragments[showIndex].isAdded()==false){
        transaction.add(R.id.fl_fragment_container,fragments[showIndex]);
        }
        transaction.show(fragments[showIndex]);
        currentIndex = showIndex;
        transaction.commit();
    }

    private void initView() {
        fragments[0] = new ShareFragment();
        fragments[1] = new MessageFragment();
        fragments[2] = new ToolsFragment();
        fragments[3] = new MeFragment();
        showFragment(currentIndex);
        rg = (RadioGroup) findViewById(R.id.rg_bottom);
        RadioButton radioButton = (RadioButton) rg.getChildAt(currentIndex);
        radioButton.setChecked(true);
        //开启消息监听
        startService(new Intent(MainActivity.this,HXMessageListenerService.class));
    }
}
