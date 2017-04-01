package com.administrator.projectsdemon;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

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
