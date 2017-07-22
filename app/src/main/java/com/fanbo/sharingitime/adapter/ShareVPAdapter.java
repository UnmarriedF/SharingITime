package com.fanbo.sharingitime.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by fanbo on 2017-07-20.
 */

public class ShareVPAdapter extends FragmentPagerAdapter {
    private Fragment[] fragments;

    public ShareVPAdapter(FragmentManager fm,Fragment[] fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
