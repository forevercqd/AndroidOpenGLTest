package com.example.lesson3.programs;

import android.content.Context;
import android.opengl.GLES20;

public class TextureShaderProgram extends ShaderProgram {

    private final String A_POSITION = "a_Position";
    private final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";
    private final String U_MATRIX = "u_Matrix";
    private final String U_TEXTURE_UNIT = "u_TextureUnit";

    private int aPositionID = -1;
    private int aTextureCoordinates = -1;
    private int uMatrix = -1;
    private int uTextureUnit = -1;


    public TextureShaderProgram(Context context, int vertexShaderResourceID, int fragmentShaderResourceID) {
        super(context, vertexShaderResourceID, fragmentShaderResourceID);
        
        aPositionID = GLES20.glGetAttribLocation(program, A_POSITION);
        aTextureCoordinates = GLES20.glGetAttribLocation(program, A_TEXTURE_COORDINATES);
        uMatrix = GLES20.glGetUniformLocation(program, U_MATRIX);
        uTextureUnit = GLES20.glGetUniformLocation(program, U_TEXTURE_UNIT);
    }

    public void useProgram(){
        super.useProgram();
    }

    public void setUniforms(float[] matrix, int textureID){
        GLES20.glUniformMatrix4fv(uMatrix, 1, false, matrix, 0);   // 设置旋转矩阵;
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0);
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureID);
        GLES20.glUniform1i(uTextureUnit, 0);       // 此处的0表示激活纹理单元0，其实是由 GLES20.GL_TEXTURE0 的下标0保持一致即可;
    }

    public int getAPositionID(){
        return aPositionID;
    }

    public int getATextureCoordinates(){
        return aTextureCoordinates;
    }
}
