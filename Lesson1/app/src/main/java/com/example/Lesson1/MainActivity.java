package com.example.Lesson1;

import androidx.appcompat.app.AppCompatActivity;

import android.opengl.GLSurfaceView;
import android.os.Bundle;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class MainActivity extends AppCompatActivity {
    private GLSurfaceView mGlSurfaceView = null;
    private boolean mRenderSeted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGlSurfaceView = new GLSurfaceView(this);

        mGlSurfaceView.setEGLContextClientVersion(2);
        mGlSurfaceView.setRenderer(new GLRender1(this));
        mRenderSeted = true;

        setContentView(mGlSurfaceView);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mRenderSeted){
            mGlSurfaceView.onResume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mRenderSeted){
            mGlSurfaceView.onPause();
        }
    }
}
