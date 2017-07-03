package com.fanbo.sharingitime.widget;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fanbo.sharingitime.R;
import com.fanbo.sharingitime.util.Const;
import com.fanbo.sharingitime.util.ScreenUtils;

import java.io.File;

/**
 * Created by fanbo on 2017/6/23.
 */

public class PhotoPopWindow extends PopupWindow implements View.OnClickListener{
    private Context mContext;
    private Handler mHandler;
    public PhotoPopWindow(Context context, Handler handler) {
        mContext = context;
        mHandler = handler;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(mContext).
                inflate(R.layout.popwindow_takephoto,new LinearLayout(mContext),false);
        setContentView(view);
        //设置点击事件
        TextView tvTakePhoto = (TextView) view.findViewById(R.id.tv_take_photo);
        TextView tvChooseFromAlbum = (TextView) view.findViewById(R.id.tv_chooseFromAlbum);
        TextView tvCancel = (TextView) view.findViewById(R.id.tv_Cancel);
        tvTakePhoto.setOnClickListener(this);
        tvChooseFromAlbum.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        //设置宽高
        setWidth(ScreenUtils.getScreenWidth(mContext));
        setHeight(LinearLayout.LayoutParams.WRAP_CONTENT);
        //设置背景图片
        //setBackgroundDrawable(new BitmapDrawable());
        //设置点击外部window消失
        setOutsideTouchable(true);
        setFocusable(true);
        //设置动画
        setAnimationStyle(R.style.take_photo_anim);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_take_photo:
                mHandler.sendEmptyMessage(Const.SELECT_PHOTO_CREAMER);
                break;
            case R.id.tv_chooseFromAlbum:
                mHandler.sendEmptyMessage(Const.SELECT_PHOTO_ABLUM);
                break;
            case R.id.tv_Cancel:
                this.dismiss();
                break;
        }
    }
}
