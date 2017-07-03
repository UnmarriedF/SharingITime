package com.fanbo.sharingitime.activity.fragment;

import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fanbo.sharingitime.R;

/**
 * Created by fanbo on 2017/6/12.
 */

public class BaseFragment extends Fragment {
    public View contentView = null;
    public RelativeLayout titlebar;

    public void initTitleBar(int leftImg,String leftStr,String midStr,String rightStr,int rightImg){
        if (contentView==null){
            return;
        }
        ImageView ivLeft = (ImageView) titlebar.findViewById(R.id.iv_title_left);
        ImageView ivRight = (ImageView) titlebar.findViewById(R.id.iv_title_right);
        TextView tvLeft = (TextView) titlebar.findViewById(R.id.tv_title_left);
        TextView tvRight = (TextView) titlebar.findViewById(R.id.tv_title_right);
        TextView tvMid = (TextView) titlebar.findViewById(R.id.tv_title_mid);
        if (leftImg!=0){
            ivLeft.setVisibility(View.VISIBLE);
            ivLeft.setImageResource(leftImg);
        }
        if (rightImg!=0){
            ivRight.setVisibility(View.VISIBLE);
            ivRight.setImageResource(rightImg);
        }
        if (!TextUtils.isEmpty(leftStr)){
            tvLeft.setVisibility(View.VISIBLE);
            tvLeft.setText(leftStr);
        }
        if (!TextUtils.isEmpty(rightStr)){
            tvRight.setVisibility(View.VISIBLE);
            tvRight.setText(rightStr);
        }
        if (!TextUtils.isEmpty(midStr)){
            tvMid.setVisibility(View.VISIBLE);
            tvMid.setText(midStr);
        }
    }

}
