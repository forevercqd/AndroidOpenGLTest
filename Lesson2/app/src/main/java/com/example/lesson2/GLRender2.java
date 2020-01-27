package com.example.lesson2;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.example.lesson2.util.ShaderHelper;
import com.example.lesson2.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class GLRender2 implements GLSurfaceView.Renderer {
    private static final String TAG = "GLRender2";
    private Context mContext = null;
    private FloatBuffer mVertexFloatBuffer = null;


    private int mVertexShaderID = -1;
    private int mFragmentShaderID = -1;
    private int mOpenGLProgramID  = -1;

    private final String A_POSITION = "a_Position";
    private final String U_COLOR = "u_Color";

    private int mAPosition = -1;
    private int mUColor = -1;

    private final int A_POSITION_SIZE = 2;
    private final int BYTE_PER_FLOAT = 4;
    private final float[] mVertexArrary = {
            -0.5F,  -0.5F,      // 0    左下角;
            0.5F,   -0.5F,      // 1    右下角
            0.5F,   0.5F,       // 2

            -0.5F,  0.5F,       // 3
            -0.5F,  -0.5F
    };
    private final int STRIDE = A_POSITION_SIZE * BYTE_PER_FLOAT;


    public GLRender2(Context context){
        mContext = context;

        mVertexFloatBuffer = ByteBuffer.allocateDirect(mVertexArrary.length * BYTE_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        mVertexFloatBuffer.put(mVertexArrary);

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        String vertexShaderString = TextResourceReader.readTextFileFromResource(mContext, R.raw.vertex_shader);
        String fragmentShaderString = TextResourceReader.readTextFileFromResource(mContext, R.raw.fragment_shader);

        // cqd.note.1 编译 shader 同时链接成 openGL 可执行文件;
        mVertexShaderID = ShaderHelper.compileVertexShader(vertexShaderString);
        mFragmentShaderID = ShaderHelper.compileFragmentShader(fragmentShaderString);

        mOpenGLProgramID = ShaderHelper.linkProgram(mVertexShaderID, mFragmentShaderID);
        GLES20.glUseProgram(mOpenGLProgramID);

        // cqd.note.2 获取 shader 中相关变量的索引值;
        mAPosition = GLES20.glGetAttribLocation(mOpenGLProgramID, A_POSITION);
        mUColor = GLES20.glGetUniformLocation(mOpenGLProgramID, U_COLOR);

        // cqd.note.3 给 shader 中相关变量赋值;
        mVertexFloatBuffer.position(0);
        GLES20.glVertexAttribPointer(mAPosition, A_POSITION_SIZE, GLES20.GL_FLOAT, true,
                STRIDE, mVertexFloatBuffer);

        GLES20.glEnableVertexAttribArray(mAPosition);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.d(TAG, "onDrawFrame");
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4); // cqd.question 为什么此处划出来的三角形不是预期的直角三角形;

        GLES20.glUniform4f(mUColor, 1.0f, 0, 0, 1.0f);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 1);

        GLES20.glUniform4f(mUColor, 0.0f, 1.0f, 0, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 1, 1);

        GLES20.glUniform4f(mUColor, 0.0f, 0, 1, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 2, 1);

        GLES20.glUniform4f(mUColor, 1.0f, 1, 1, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 3, 1);

    }
}
