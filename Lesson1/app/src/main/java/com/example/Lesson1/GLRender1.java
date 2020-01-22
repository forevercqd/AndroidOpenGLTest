package com.example.Lesson1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import com.example.Lesson1.util.LoggerConfig;
import com.example.Lesson1.util.ShaderHelper;
import com.example.Lesson1.util.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;


public class GLRender1 implements GLSurfaceView.Renderer {
    private static final int BYTES_PER_FLOAT = 4;
    private final Context mContext;
    private FloatBuffer vertexData;
    private static final int POSITION_COMOPNENT_COUNT = 2;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMOPNENT_COUNT+COLOR_COMPONENT_COUNT)*BYTES_PER_FLOAT;

    private final String A_POSITION = "a_Position";
    private final String A_COLOR = "a_Color";


    private int program;
    float[] tableVertices = {
            //Order of coordinates:X,Y,R,G,B


            //Triangle Fan
            0f,     0f,     1.0f,1.0f,1.0f,
            -0.5f,  -0.5f,  0.7f,0.7f,0.7f,
            0.5f,   -0.5f,  0.7f,0.7f,0.7f,
            0.5f,   0.5f,   0.7f,0.7f,0.7f,
            -0.5f,  0.5f,   0.7f,0.7f,0.7f,
            -0.5f,  -0.5f,  0.7f,0.7f,0.7f,

            -0.5f,  0f,     1.0f,0.0f,0.0f,
            0.5f,   0f,     1.0f,0.0f,0.0f,

            0f,     -0.25f, 0.0f,0.0f,1.0f,
            0f,     0.25f,  1.0f,0.0f,0.0f
    };


    private int aColorLocation;
    private int aPostionLocation;

    GLRender1(Context context){
        mContext = context;

        vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT).order(
                ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(tableVertices);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        String vertexShaderSource   = TextResourceReader.readTextFileFromResource(mContext,R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext,R.raw.simple_fragment_shader);

        int vertexShader   = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader,fragmentShader);

        if(LoggerConfig.ON){
            ShaderHelper.validateProgram(program);
        }

        GLES20.glUseProgram(program);
        aColorLocation = GLES20.glGetAttribLocation(program,A_COLOR);
        aPostionLocation = GLES20.glGetAttribLocation(program,A_POSITION);

        vertexData.position(0);
        GLES20.glVertexAttribPointer(aPostionLocation,POSITION_COMOPNENT_COUNT,GLES20.GL_FLOAT,false,STRIDE,vertexData);
        GLES20.glEnableVertexAttribArray(aPostionLocation);

        vertexData.position(POSITION_COMOPNENT_COUNT);
        GLES20.glVertexAttribPointer(aColorLocation,COLOR_COMPONENT_COUNT,GLES20.GL_FLOAT,false,STRIDE,vertexData);
        GLES20.glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);

        // 画三角形
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);

        // 画横线
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

        // 画竖线
        GLES20.glDrawArrays(GLES20.GL_LINES, 8, 2);

        // 画点
        GLES20.glDrawArrays(GLES20.GL_POINTS,8, 1);
        GLES20.glDrawArrays(GLES20.GL_POINTS,9, 1);
    }
}
