package com.example.lesson3.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

import com.example.lesson3.util.LoggerConfig;

/**
 * Created by Administrator on 2016/7/5.
 */
public class TextureHelper {

  private final static String TAG = "TextureHelper";

  public static int loadTexture(Context context,int resourceId){  // cqd.note 加载本地图像至新建的纹理中;
    final int[] textureObjectIds = new int[1];
    GLES20.glGenTextures(1,textureObjectIds,0); // cqd.note 创建纹理;
    if(textureObjectIds[0] == 0 ){
      if(LoggerConfig.ON){
        Log.w(TAG,"Could not generate a new OpenGL texture object.");
      }
      return 0;
    }
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inScaled = false;

    final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),resourceId,options);

    if(bitmap==null){
      if(LoggerConfig.ON){
        Log.w(TAG,"Resource ID "+resourceId+" could not be decoded");
      }
      GLES20.glDeleteTextures(1,textureObjectIds,0);
      return 0;
    }
    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,textureObjectIds[0]); // cqd.note 将刚创建的纹理绑定至 GL_TEXTURE_2D 目标上;
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MIN_FILTER,GLES20.GL_LINEAR_MIPMAP_LINEAR);
    GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D,GLES20.GL_TEXTURE_MAG_FILTER,GLES20.GL_LINEAR);
    GLUtils.texImage2D(GLES20.GL_TEXTURE_2D,0,bitmap,0);  // glteximage2d 功能是根据指定的参数，生成一个2D纹理（Texture），那 glGenTextures 表示创建一个texture， 两者间关系是什么？一个是表示创建一个画布，一个是将画面的内容画到画布上吗？
    GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);  // 生成需要的所有级别的纹理;

    bitmap.recycle();

    GLES20.glBindTexture(GLES20.GL_TEXTURE_2D,0);
    return textureObjectIds[0];
  }
}
