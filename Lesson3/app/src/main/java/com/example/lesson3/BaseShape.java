package com.example.lesson3;

import android.opengl.GLES20;

import com.example.lesson3.programs.ColorShaderProgram;
import com.example.lesson3.util.VertexArray;

public class BaseShape {
    public final static int BYTES_PER_FLOAT = 4;
    public final static int POSITION_COMPENT_COUNT = 2;
    public final static int COLOR_COMPENT_COUNT = 3;
    public final static int STRIDE = (POSITION_COMPENT_COUNT + COLOR_COMPENT_COUNT) * BYTES_PER_FLOAT;

    VertexArray mVertexDataBuffer = null;


    public static float[] vertexData = {
            -0.5F,  -0.5F,   1.0f, 0.0f, 0.0f,     // 0    左下角;
            0.5F,   -0.5F,   0.0f, 1.0f, 0.0f,     // 1    右下角;
            0.5F,   0.5F,    0.0f, 0.0f, 1.0f,     // 2    右上角;

            -0.5F,  0.5F,    1.0f, 1.0f, 1.0f,     // 3    左上角;
    };

    public BaseShape(){
        mVertexDataBuffer = new VertexArray(vertexData);
    }

    public void bindData(ColorShaderProgram colorShaderProgram){
        mVertexDataBuffer.setVertexPointer(colorShaderProgram.getPositionAttribLocation(),0, POSITION_COMPENT_COUNT, STRIDE);
        mVertexDataBuffer.setVertexPointer(colorShaderProgram.getColorAttribLocation(), POSITION_COMPENT_COUNT, COLOR_COMPENT_COUNT, STRIDE);
    }

    public void draw(){
//        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
        GLES20.glDrawArrays(GLES20.GL_POINTS, 0, 4);
    }
}
