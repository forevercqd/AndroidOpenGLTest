package com.example.lesson3;

import android.opengl.GLES20;

import com.example.lesson3.programs.TextureShaderProgram;
import com.example.lesson3.util.VertexArray;

public class TextureShape {
    VertexArray mVertexFloatBuffer = null;

    public final static int BYTES_PER_FLOAT = 4;
    public final static int POSITION_COMPENT_COUNT = 2;
    public final static int TEXTURE_COMPENT_COUNT = 2;
    public final static int STRIDE = (POSITION_COMPENT_COUNT + TEXTURE_COMPENT_COUNT) * BYTES_PER_FLOAT;

    public static final float[] vertex_data = {
            -0.5F,  -0.5F,      0.0f, 0.0f,
            0.5F,   -0.5F,      1.0f, 0.0f,
            0.5F,   0.5F,       1.0f, 1.0f,
            -0.5F,  0.5F,       0.0f, 1.0f
    };

    public TextureShape(){
        mVertexFloatBuffer = new VertexArray(vertex_data);
    }

    public void bindData(TextureShaderProgram textureShaderProgram){
        mVertexFloatBuffer.setVertexPointer(textureShaderProgram.getAPositionID(), 0, POSITION_COMPENT_COUNT, STRIDE);
        mVertexFloatBuffer.setVertexPointer(textureShaderProgram.getATextureCoordinates(), POSITION_COMPENT_COUNT, TEXTURE_COMPENT_COUNT, STRIDE);
    }

    public void draw(){
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 4);
    }

}
