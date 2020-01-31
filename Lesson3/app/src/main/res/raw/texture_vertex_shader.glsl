uniform mat4 u_Matrix;
attribute vec4 a_Position;
attribute vec4 a_TextureCoordinates;

varying vec2 v_TextureCoordinates;

void main()
{
  v_TextureCoordinates = (u_Matrix * a_TextureCoordinates).xy;
  gl_Position = a_Position;
}
