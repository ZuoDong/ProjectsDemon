package com.administrator.projectsdemon.ui;

import android.content.Context;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.administrator.projectsdemon.R;

import java.util.List;

public class LocationActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView location_info;
    private TextView content_location;
    private EditText latitude;
    private EditText longitude;
    private Button location_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        initView();
        initLocationInfo();
    }

    private void initLocationInfo() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        String allEnableProviders = ListToString(providers);
    }

    private String ListToString(List<String> providers) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < providers.size(); i++) {
            stringBuilder.append(providers.get(i));
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    private void initView() {
        location_info = (TextView) findViewById(R.id.location_info);
        content_location = (TextView) findViewById(R.id.content_location);
        latitude = (EditText) findViewById(R.id.latitude);
        longitude = (EditText) findViewById(R.id.longitude);
        location_btn = (Button) findViewById(R.id.location_btn);

        location_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_btn:

                break;
        }
    }

    private void submit() {
        // validate
        String latitudeString = latitude.getText().toString().trim();
        if (TextUtils.isEmpty(latitudeString)) {
            Toast.makeText(this, "latitude", Toast.LENGTH_SHORT).show();
            return;
        }

        String longitudeString = longitude.getText().toString().trim();
        if (TextUtils.isEmpty(longitudeString)) {
            Toast.makeText(this, "longitude", Toast.LENGTH_SHORT).show();
            return;
        }

        // TODO validate success, do something


    }
}
