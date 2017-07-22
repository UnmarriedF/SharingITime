package com.fanbo.sharingitime.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 *
 *  Created by fanbo on 2017/6/11.
 */

public class GuideCircleView extends View {
    private int circleNum = 4;

    public GuideCircleView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public GuideCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public GuideCircleView(Context context) {
        super(context);
    }

    /**
     * 第一个空心圆的圆心位置
     */
    private float strokeStartPos, fillStartPos;
    private int height;

    /**
     * 当屏幕尺寸发生变化时执行此方法
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        fillStartPos = strokeStartPos = w / 2 - 12 * radius;
        height = h;
    }

    //半径
    private int radius = 16;
    //简化锯齿
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(3);
        paint.setColor(Color.WHITE);
        //绘制n个空心圆
        for (int i = 0; i < circleNum; i++) {
            int distance =  i  * 8 * radius;
            canvas.drawCircle(strokeStartPos + distance, height / 2, radius, paint);
        }
        //绘制一个实心圆
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
        canvas.drawCircle(fillStartPos, height / 2, radius, paint);
    }

    public void change(int position, float offset) {
        //改变实心圆绘制的位置
        fillStartPos = strokeStartPos + (position + offset) * radius * 8;
        //重新绘制
        invalidate();
    }
}
