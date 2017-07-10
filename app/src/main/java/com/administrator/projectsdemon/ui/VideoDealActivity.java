package com.administrator.projectsdemon.ui;

import android.opengl.GLSurfaceView;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

import com.administrator.projectsdemon.R;
import com.administrator.projectsdemon.widget.GLVideoRenderer;

public class VideoDealActivity extends AppCompatActivity {

    private GLSurfaceView glSurfaceView;
    private GLVideoRenderer glRenderer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video_deal);
        glSurfaceView= (GLSurfaceView) findViewById(R.id.glSurfaceView);
        glSurfaceView.setEGLContextClientVersion(2);
        glRenderer=new GLVideoRenderer(this, Environment.getExternalStorageDirectory().getPath()+"/DCIM/Camera/VID_20170710_172102.mp4");
        glSurfaceView.setRenderer(glRenderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        glRenderer.getMediaPlayer().release();
    }

    @Override
    protected void onPause() {
        super.onPause();
        glSurfaceView.onPause();
        glRenderer.getMediaPlayer().pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        glSurfaceView.onResume();
    }
}
