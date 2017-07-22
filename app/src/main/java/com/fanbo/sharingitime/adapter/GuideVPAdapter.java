package com.fanbo.sharingitime.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.activity.RegisterActivity;
import com.fanbo.sharingitime.util.ToastUtil;

/**
 * Created by fanbo on 2017/6/11.
 */

public class GuideVPAdapter extends PagerAdapter {
    private int[] images;
    private Context mContext;

    public GuideVPAdapter(Context context, int[] images) {
        this.images = images;
        mContext = context;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        RelativeLayout relativeLayout = (RelativeLayout) View.inflate(mContext,R.layout.guide_view_item,null);
        ImageView iv = (ImageView) relativeLayout.findViewById(R.id.iv_guide);
        Glide.with(iv.getContext()).load(images[position]).into(iv);

        if (position==3) {
            Button btn = (Button) relativeLayout.findViewById(R.id.btn_guide);
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mContext.startActivity(new Intent(mContext, RegisterActivity.class));
                    // TODO: 2017/6/30 将GuideActivity  finish
                }
            });
        }
        container.addView(relativeLayout);
        return relativeLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
