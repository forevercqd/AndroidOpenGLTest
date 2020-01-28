package com.example.lesson3.programs;

import android.content.Context;
import android.opengl.GLES20;

import com.example.lesson3.util.ShaderHelper;
import com.example.lesson3.util.TextResourceReader;

public class ShaderProgram {
    protected int program;

    protected ShaderProgram(Context context, int vertexShaderResourceID, int fragmentShaderResourceID){
        program = ShaderHelper.buildProgram(
                TextResourceReader.readTextFileFromResource(context, vertexShaderResourceID),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceID)
        );
    }

    protected void useProgram(){
        GLES20.glUseProgram(program);
    }
}
