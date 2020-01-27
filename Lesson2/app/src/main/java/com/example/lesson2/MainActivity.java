package com.example.lesson2;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView = null;
    private GLRender2 mGLRender2 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGLSurfaceView = new GLSurfaceView(this);
        mGLSurfaceView.setEGLContextClientVersion(2);

        mGLRender2 = new GLRender2(this);
        mGLSurfaceView.setRenderer(mGLRender2);
        setContentView(mGLSurfaceView);
    }
}
