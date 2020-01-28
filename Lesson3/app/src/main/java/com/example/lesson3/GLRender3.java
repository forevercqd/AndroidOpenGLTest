package com.example.lesson3;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.example.lesson3.programs.ColorShaderProgram;
import com.example.lesson3.programs.TextureShaderProgram;
import com.example.lesson3.util.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class GLRender3 implements GLSurfaceView.Renderer {
    private static final String TAG = "GLRender3";
    private Context mContext = null;
    private int mTextureID = -1;



    private final float[] projectionMatrix = {
            1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f
    };

    private ColorShaderProgram mColorShaderProgram = null;
    private BaseShape mBaseShape = null;

    TextureShaderProgram mTextureShaderProgram = null;
    private TextureShape mTextureShape = null;

    public GLRender3(Context context){
        mContext = context;
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // cqd.note.1 加载本地的图像，并上传至新建的2D纹理，同时返回该纹理ID值;
        mTextureID = TextureHelper.loadTexture(mContext, R.drawable.air_hockey_surface);

        mTextureShaderProgram = new TextureShaderProgram(mContext, R.raw.texture_vertex_shader, R.raw.texture_fragment_shader);
        mTextureShape = new TextureShape();

        mColorShaderProgram = new ColorShaderProgram(mContext, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);
        mBaseShape = new BaseShape();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        mColorShaderProgram.useProgram();
        mBaseShape.bindData(mColorShaderProgram);
        mBaseShape.draw();

        mTextureShaderProgram.useProgram();
        mTextureShaderProgram.setUniforms(projectionMatrix, mTextureID);
        mTextureShape.bindData(mTextureShaderProgram);
        mTextureShape.draw();

        Log.d(TAG, "cqd, onDrawFrame, finish");
    }
}
