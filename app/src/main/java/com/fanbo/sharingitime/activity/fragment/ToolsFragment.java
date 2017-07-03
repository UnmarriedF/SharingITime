package com.fanbo.sharingitime.activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fanbo.sharingitime.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ToolsFragment extends BaseFragment {


    public ToolsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fragment_tools, container, false);
        return contentView;
    }

}
