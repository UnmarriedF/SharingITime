package com.fanbo.sharingitime.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.fragment.MeFragment;
import com.fanbo.sharingitime.activity.fragment.MessageFragment;
import com.fanbo.sharingitime.activity.fragment.ShareFragment;
import com.fanbo.sharingitime.activity.fragment.ToolsFragment;

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
                currentIndex = rg.indexOfChild(rg.findViewById(i));
                showFragment(currentIndex);
            }
        });
    }

    private void showFragment(int currentIndex) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fl_fragment_container,fragments[currentIndex]);
        transaction.show(fragments[currentIndex]);
        transaction.commit();
    }

    private void initView() {
        fragments[0] = new ShareFragment();
        fragments[1] = new MessageFragment();
        fragments[2] = new ToolsFragment();
        fragments[3] = new MeFragment();
        showFragment(currentIndex);
        rg = (RadioGroup) findViewById(R.id.rg_bottom);
    }
}
