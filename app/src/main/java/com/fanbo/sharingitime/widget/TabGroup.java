package com.fanbo.sharingitime.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RadioGroup;

/**
 * Created by fanbo on 2017-07-20.
 */
    public class TabGroup extends RadioGroup {
        public TabGroup(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
        public TabGroup(Context context) {
            super(context);
        }
        private int redLineStartX;

        /**当绘制view的子元素时默认会执行此方法*/
        @Override
        protected void dispatchDraw(Canvas canvas) {
            super.dispatchDraw(canvas);
            //1.保存原有绘制状态
            canvas.save();
            //2.绘制新的内容
            //2.1.在元素底部绘制一条灰色的线
            Paint paint=new Paint();//画笔
            paint.setStyle(Paint.Style.FILL);//绘制线条
            //paint.setStrokeWidth(2);//设置线条粗细
            //paint.setColor(Color.GRAY);

//            canvas.drawLine(
//                    0,//startX
//                    getHeight(),//startY
//                    getWidth(),//stopX
//                    getHeight(),//stopY
//                    paint);
            //2.2.在元素的底部绘制一条红色的线
            paint.setStrokeWidth(12);//设置线条粗细
            paint.setColor(Color.RED);
            canvas.drawLine(
                    redLineStartX,//startX
                    getHeight(),//startY
                    redLineStartX+getWidth()/getChildCount(),//stopX
                    getHeight(),//stopY
                    paint);
            //3.恢复原有绘制状态
            canvas.restore();
        }
        public void onCheckedChanged(int position){
            redLineStartX=position*(getWidth()/getChildCount());
            invalidate();//此时会重新绘制view,会重新执行dispatchDraw
        }
    }
