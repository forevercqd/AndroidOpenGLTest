package com.example.lesson3.programs;

import android.content.Context;
import android.opengl.GLES20;

public class ColorShaderProgram extends ShaderProgram {
    public final String A_POSITION = "a_Position";
    public final String A_COLOR = "a_Color";

    private int aPosition = -1;
    private int aColor = -1;

    public ColorShaderProgram(Context context, int vertexShaderResourceID, int fragmentShaderResourceID) {
        super(context, vertexShaderResourceID, fragmentShaderResourceID);

        aPosition = GLES20.glGetAttribLocation(program, A_POSITION);
        aColor = GLES20.glGetAttribLocation(program, A_COLOR);
    }

    public void useProgram(){
        super.useProgram();
    }

    public int getPositionAttribLocation(){
        return aPosition;
    }

    public int getColorAttribLocation(){
        return aColor;
    }

}
