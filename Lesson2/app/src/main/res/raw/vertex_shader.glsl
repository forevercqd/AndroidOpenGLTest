attribute vec4 a_Position;
attribute vec3 a_Color;


varying vec4 v_Color;

void main() {
    gl_Position = a_Position;
    gl_PointSize = 20.0;

    v_Color = vec4(a_Color, 1.0f);
}
