package com.administrator.projectsdemon.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 2017/3/7.
 *
 * 绘制一个可以随手指移动的小球
 */

public class MovingBoll extends View {

    public float X = 50;
    public float Y = 50;
    public float radius = 30;

    Paint paint = new Paint();

    public MovingBoll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.BLUE);
        canvas.drawCircle(X,Y,radius,paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //重绘
        this.X = event.getX();
        this.Y = event.getY();
        invalidate();
        return true;
    }

}
