package com.administrator.projectsdemon.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Administrator on 2017/3/14.
 */

public class ClockView extends View {

    private final Paint mPaint;

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(canvas.getWidth()/2,200);
        Paint paint = new Paint();
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(0,0,100,paint);

        for (int i = 0; i < canvas.getWidth(); i=i+100) {
            canvas.drawLine(i,0,i,canvas.getHeight(),paint);
        }
        for (int j = 0; j < canvas.getHeight(); j=j+100) {
            canvas.drawLine(0,j,canvas.getWidth(),j,paint);
        }

        //绘制文字
        canvas.save();
        canvas.translate(-75,-75);
        Path path = new Path();
        path.addArc(new RectF(0,0,150,150),-180,180);
        Paint citePaint = new Paint(mPaint);
        citePaint.setTextSize(14);
        citePaint.setStrokeWidth(1);
        canvas.drawTextOnPath("绘制表盘~",path,28,0,citePaint);
        canvas.restore();

        Paint tmpPaint = new Paint(mPaint);
        tmpPaint.setStrokeWidth(1);

        //画刻度
        float y = 100;
        int count = 60;
        for (int i = 0; i < count; i++) {
            if(i%5==0){
                canvas.drawLine(0f,y,0f,y+12f,tmpPaint);
                canvas.drawText(String.valueOf(i/5+1),-4f,y+25f,tmpPaint);
            }else{
                canvas.drawLine(0f,y,0f,y+5f,tmpPaint);
            }
            canvas.rotate(360/count,0,0);
        }

        //绘制指针
        tmpPaint.setColor(Color.GRAY);
        tmpPaint.setStrokeWidth(4);
        canvas.drawCircle(0, 0, 7, tmpPaint);
        tmpPaint.setStyle(Paint.Style.FILL);
        tmpPaint.setColor(Color.YELLOW);
        canvas.drawCircle(0, 0, 5, tmpPaint);
        canvas.drawLine(0, 10, 0, -65, mPaint);
    }
}
