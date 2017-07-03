package com.fanbo.sharingitime.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.fanbo.sharingitime.R;

/**
 *
 * Created by fanbo on 2017/6/13.
 */

public class CircleImageView extends ImageView {
    private Paint mPaintBorder;
    private Paint mPaintCircle;
    /**
     * 图像着色器，用于画圆
     */
    private BitmapShader mBitmapShader;
    /**
     * 图像变换处理器，用于缩放图片以适应view大小
     */
    private Matrix mMatrix;
    private int mWeight;
    private int mHeight;
    private int mRadius;
    private int mCircleBorderWidth;
    private int mCircleBorderColor;


    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CircleHead);
        int n = typedArray.getIndexCount();
        for (int i = 0; i < n; i++) {
            int attr = typedArray.getIndex(i);
            switch (attr) {
                case R.styleable.CircleHead_circleBorderHeadWidth:
                    mCircleBorderWidth = (int) typedArray.getDimension(attr, 0);
                    break;
                case R.styleable.CircleHead_ringHeadColor:
                    mCircleBorderColor = typedArray.getColor(attr, Color.WHITE);
                    break;
            }

        }
        init();
    }

    private void init() {
        mMatrix = new Matrix();


        mPaintCircle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintCircle.setStrokeWidth(12);

        mPaintBorder = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintBorder.setStyle(Paint.Style.STROKE);
        mPaintBorder.setStrokeWidth(mCircleBorderWidth);
        mPaintBorder.setColor(mCircleBorderColor);


    }

    private void setBitmapShader() {
        //将图片转换成bitmap
        Drawable drawable = getDrawable();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
        Bitmap bitmap = bitmapDrawable.getBitmap();
        //将bitmap放进图像着色器，后面两个模式是x，y轴的缩放模式，CLAMP表示拉伸
        mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        //初始化图片与view之间伸缩比例，因为比例一般非整数，所以用float，免得精度丢失
        float scale = 1.0f;
        int bitmapSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        scale = mWeight * 1.0f / bitmapSize;
        //利用这个图像变换处理器，设置伸缩比例，长宽以相同比例伸缩
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        //为画笔套上一个Shader的笔套
        mPaintCircle.setShader(mBitmapShader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //这里注释掉onDraw是为了不绘制原来的画布,如果使用的话就意味着又是渲染一层，容易造成OOM
        //super.onDraw(canvas);
        if (getDrawable() != null) {
            setBitmapShader();
            canvas.drawCircle(mWeight / 2, mHeight / 2, mRadius, mPaintCircle);
            canvas.drawCircle(mWeight / 2, mHeight / 2, mRadius + mCircleBorderWidth / 2, mPaintBorder);
        } else {
            super.onDraw(canvas);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWeight = getWidth();
        mHeight = getHeight();
        int mCircleSize = Math.min(mWeight, mHeight);
        mRadius = mCircleSize / 2-mCircleBorderWidth;
    }
}
