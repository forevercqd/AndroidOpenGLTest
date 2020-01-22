package com.example.Lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mGlSurfaceView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGlSurfaceView = new GLSurfaceView(this);

        mGlSurfaceView.setEGLContextClientVersion(2);
        mGlSurfaceView.setRenderer(new GLRender1());

        setContentView(mGlSurfaceView);
    }
}
