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

        // cqd.note.1 创建 GLSurfaceView, 然后调用 setContentView 将该 GLSurfaceView 替换掉默认的视图布局;
        mGlSurfaceView = new GLSurfaceView(this);

        mGlSurfaceView.setEGLContextClientVersion(2);

        // cqd.note.2 需要往自定义的 GLSurfaceView 上画内容时，需要重写 GLSurfaceView 的 mRenderer;
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
