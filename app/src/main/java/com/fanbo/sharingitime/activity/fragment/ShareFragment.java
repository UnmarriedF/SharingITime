package com.fanbo.sharingitime.activity.fragment;


import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.adapter.ShareVPAdapter;
import com.fanbo.sharingitime.widget.TabGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class ShareFragment extends BaseFragment {

    private Fragment[] fragments = new Fragment[3];
    private ViewPager vp;
    private TabGroup tabGroup;
    private ShareVPAdapter vpAdapter;
    private ImageView ivAdd;
    private ImageView ivMakeShare;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_share, container, false);
        initView();
        addListener();
        return contentView;
    }

    private void addListener() {
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                RadioButton rbtn = (RadioButton) tabGroup.getChildAt(position);
                rbtn.setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int index = group.indexOfChild(group.findViewById(checkedId));
                vp.setCurrentItem(index);
                tabGroup.onCheckedChanged(index);
            }
        });
        ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        ivMakeShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initView() {
        fragments[0] = new HotFragment();
        fragments[1] = new ConcertFragment();
        fragments[2] = new CommendFragment();
        vp = (ViewPager) contentView.findViewById(R.id.vp_share);
        vpAdapter = new ShareVPAdapter(getChildFragmentManager(), fragments);
        vp.setAdapter(vpAdapter);
        tabGroup = (TabGroup) contentView.findViewById(R.id.rg_title);
        ivAdd = (ImageView) contentView.findViewById(R.id.iv_add);
        ivMakeShare = (ImageView) contentView.findViewById(R.id.iv_make_share);
    }
}
