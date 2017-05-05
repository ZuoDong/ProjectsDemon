package com.administrator.projectsdemon.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.administrator.projectsdemon.R;
import com.administrator.projectsdemon.widget.AutoScaleTextView;
import com.blankj.utilcode.util.ConvertUtils;

import java.util.Random;

public class AutoScaleTextActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoScaleTextView auto_scale_tv;
    private TextView show_arg_tv;
    private Button add_text_btn;
    private Button reduce_text_btn;
    private Button clear_text_btn;
    private Button exchange_text_btn;
    private String[] str = {"人类的未来就是失控，就是人与机器共生、共存。机器越来越人性化， 人越来越机器化。《失控》这本书，主要就体现了这一思想。 本文选自《全栈数据之门》一书。",
    "二哈受死吧","人类的未来就是失控，就是人与机器共生、共存。"};
    private Random random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_scale_text);
        initView();
    }

    private void initView() {
        auto_scale_tv = (AutoScaleTextView) findViewById(R.id.auto_scale_tv);
        show_arg_tv = (TextView) findViewById(R.id.show_arg_tv);
        add_text_btn = (Button) findViewById(R.id.add_text_btn);
        reduce_text_btn = (Button) findViewById(R.id.reduce_text_btn);

        add_text_btn.setOnClickListener(this);
        reduce_text_btn.setOnClickListener(this);
        auto_scale_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int i = ConvertUtils.px2dp(auto_scale_tv.getTextSize());
                show_arg_tv.setText("宽 200dp\n高 100dp" + "\n最小Size 8dp" + "\n当前Size " + i + "dp");
            }
        });
        clear_text_btn = (Button) findViewById(R.id.clear_text_btn);
        clear_text_btn.setOnClickListener(this);
        exchange_text_btn = (Button) findViewById(R.id.exchange_text_btn);
        exchange_text_btn.setOnClickListener(this);
        random = new Random();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_text_btn:
                auto_scale_tv.append(",皮皮虾我们走");
                break;
            case R.id.reduce_text_btn:
                String text = auto_scale_tv.getText().toString();
                int index = text.lastIndexOf(",");
                if (index > 0) {
                    auto_scale_tv.setText(text.substring(0, index));
                }
                break;
            case R.id.clear_text_btn:
                auto_scale_tv.setText("");
                break;
            case R.id.exchange_text_btn:
                auto_scale_tv.setText(str[random.nextInt(3)]);
                break;
        }
    }
}
