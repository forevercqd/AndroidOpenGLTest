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

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;


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
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        // cqd.note.3 利用工具类编译 vetex_shader 及 fragment_shader, 并最终链接生成 可执行程序;
        String vertexShaderSource   = TextResourceReader.readTextFileFromResource(mContext,R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext,R.raw.simple_fragment_shader);

        int vertexShader   = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        program = ShaderHelper.linkProgram(vertexShader,fragmentShader);

        if(LoggerConfig.ON){
            ShaderHelper.validateProgram(program);
        }

        glUseProgram(program);

        // cqd.note.4 获取 gl_FragColor 与 gl_Position 对应值的shader 可执行文件中的索引值;
        // 此处需要区分 attribute、varying、 union 间区别;
        // attribute
        aColorLocation = glGetAttribLocation(program,A_COLOR);
        aPostionLocation = glGetAttribLocation(program,A_POSITION);

        // cqd.note.5 设置顶点坐标坐标与纹理坐标(shader 中顶点坐标与纹理坐标是打包存放，即 V1_F1_V2_F2_..._Vn_Fn);
        // OpenGL着色器是用OpenGL着色器语言(OpenGL Shading Language, GLSL)写成;
        // 图形渲染管线可以被划分为两个主要部分：第一部分把你的3D坐标转换为2D坐标，第二部分是把2D坐标转变为实际的有颜色的像素。
        // 具体可参考: https://learnopengl-cn.github.io/01%20Getting%20started/04%20Hello%20Triangle/
        // glVertexAttribPointer 作用是告知 opengl 如何解析顶点数据;
        // glEnableVertexAttribArray 作用是启用该顶点属性;
        vertexData.position(0);
        glVertexAttribPointer(aPostionLocation,POSITION_COMOPNENT_COUNT,GL_FLOAT,false,STRIDE,vertexData);
        glEnableVertexAttribArray(aPostionLocation);

        vertexData.position(POSITION_COMOPNENT_COUNT);
        glVertexAttribPointer(aColorLocation,COLOR_COMPONENT_COUNT,GL_FLOAT,false,STRIDE,vertexData);
        glEnableVertexAttribArray(aColorLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        glClear(GL_COLOR_BUFFER_BIT);

        // cqd.note.6 每帧数据的回调;
        // 画三角形
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);

        // 画横线
        glDrawArrays(GL_LINES, 6, 2);

        // 画竖线
        glDrawArrays(GL_LINES, 8, 2);

        // 画点
        glDrawArrays(GL_POINTS,8, 1);
        glDrawArrays(GL_POINTS,9, 1);
    }
}
