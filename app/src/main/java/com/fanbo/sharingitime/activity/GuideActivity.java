package com.fanbo.sharingitime.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.adapter.GuideVPAdapter;
import com.fanbo.sharingitime.widget.GuideCircleView;

public class GuideActivity extends BaseActivity {

    private ViewPager vp;
    private GuideCircleView circleView;
    private int[] images = {R.drawable.guide1, R.drawable.guide2, R.drawable.guide3, R.drawable.guide4};
    private GuideVPAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gudie);
        checkStorage();
        initViews();
        addListener();

    }

    private void addListener() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                circleView.change(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initViews() {
        vp = (ViewPager) findViewById(R.id.vp_guide);
        adapter = new GuideVPAdapter(this, images);
        vp.setAdapter(adapter);
        circleView = (GuideCircleView) findViewById(R.id.cv_guide);
    }
}
