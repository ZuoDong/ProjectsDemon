package com.administrator.projectsdemon.ui;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.administrator.projectsdemon.R;

public class MultiplePointTouchActivity extends AppCompatActivity implements View.OnTouchListener{

    private ImageView drag_view;

    //缩放控制
    private Matrix matrix = new Matrix();
    private Matrix savematrix = new Matrix();

    //不同状态表示
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private int mode = NONE;

    //定义第一个按下的点，两只接触点的中点，以及两点间的距离
    private PointF startPoint = new PointF();
    private PointF midPoint = new PointF();
    private float oriDis = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_point_touch);
        initView();
    }

    private void initView() {
        drag_view = (ImageView) findViewById(R.id.drag_view);
        drag_view.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        ImageView view = (ImageView) v;
        //event.getAction() & MotionEvent.ACTION_MASK用来判断多点触控
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                matrix.set(view.getImageMatrix());
                savematrix.set(matrix);
                startPoint.set(event.getX(),event.getY());
                mode = DRAG;
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                oriDis = distances(event);
                if(oriDis > 10f){
                    savematrix.set(matrix);
                    midPoint = middle(event);
                    mode = ZOOM;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:
                mode = NONE;
                break;
            case MotionEvent.ACTION_MOVE:
                if(mode == DRAG){
                    matrix.set(savematrix);
                    matrix.postTranslate(event.getX() - startPoint.x,event.getY() - startPoint.y);
                }else if(mode == ZOOM){
                    float newDis = distances(event);
                    if(newDis > 10f){
                        matrix.set(savematrix);
                        float scale = newDis / oriDis;
                        matrix.postScale(scale,scale,midPoint.x,midPoint.y);
                    }
                }
                break;
        }
        view.setImageMatrix(matrix);
        return true;
    }

    //中间点计算
    private PointF middle(MotionEvent event) {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        return new PointF(x / 2,y / 2);
    }

    //两点距离
    private float distances(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }
}
