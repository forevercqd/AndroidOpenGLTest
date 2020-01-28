package com.example.lesson3.util;

import android.opengl.GLES20;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import static com.example.lesson3.BaseShape.BYTES_PER_FLOAT;

public class VertexArray {
    FloatBuffer mVertexFloatBuffer;
    public VertexArray(float[] vertexData){
        mVertexFloatBuffer = ByteBuffer.allocateDirect(vertexData.length * BYTES_PER_FLOAT)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer();

        mVertexFloatBuffer.put(vertexData);
    }

    public void setVertexPointer(int attribLocation, int startPosition, int compentCount, int stride){
        mVertexFloatBuffer.position(startPosition);
        GLES20.glVertexAttribPointer(attribLocation, compentCount, GLES20.GL_FLOAT, true, stride, mVertexFloatBuffer);
        GLES20.glEnableVertexAttribArray(attribLocation);
    }
}
