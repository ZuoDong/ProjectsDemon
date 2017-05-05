package com.administrator.projectsdemon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.administrator.projectsdemon.ui.AutoScaleTextActivity;
import com.administrator.projectsdemon.ui.CircleImageActivity;
import com.administrator.projectsdemon.ui.ClockActivity;
import com.administrator.projectsdemon.ui.FoldingActivity;
import com.administrator.projectsdemon.ui.LocationActivity;
import com.administrator.projectsdemon.ui.MovingBollActivity;
import com.administrator.projectsdemon.ui.MultiplePointTouchActivity;
import com.administrator.projectsdemon.ui.RxJavaActivity;
import com.administrator.projectsdemon.ui.WipeClothActivity;

public class MainActivity extends AppCompatActivity {

    private LinearLayout listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        listView = (LinearLayout) findViewById(R.id.total_grid_layout);
        addData("折叠View",FoldingActivity.class);
        addData("移动小球",MovingBollActivity.class);
        addData("两点触控",MultiplePointTouchActivity.class);
        addData("钟表",ClockActivity.class);
        addData("擦衣服",WipeClothActivity.class);
        addData("圆角图片",CircleImageActivity.class);
        addData("rxjava",RxJavaActivity.class);
        addData("自动缩放文字大小的TextView", AutoScaleTextActivity.class);
        addData("根据经纬度进行定位",LocationActivity.class);
    }

    private void addData(String content, final Class clzz) {
        Button button = new Button(this);
        button.setText(content);
        button.setTextColor(Color.BLACK);
        button.setGravity(Gravity.CENTER);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,clzz));
            }
        });
        listView.addView(button, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }
}
