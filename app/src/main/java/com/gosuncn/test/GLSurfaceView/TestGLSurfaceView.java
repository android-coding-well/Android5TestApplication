package com.gosuncn.test.GLSurfaceView;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by hwj on 2016/1/6.
 */
public class TestGLSurfaceView extends GLSurfaceView {
    DemoRenderer renderer;
    Cube cube;
    /**
     * Standard View constructor. In order to render something, you
     * must call {@link #setRenderer} to register a renderer.
     *
     * @param context
     */
    public TestGLSurfaceView(Context context) {
        super(context);
        //为了可以激活log和错误检查，帮助调试3D应用，需要调用setDebugFlags()。
        this.setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
        renderer=new DemoRenderer();
        this.setRenderer(renderer);
        cube=new Cube();
    }

    /**
     * Standard View constructor. In order to render something, you
     * must call {@link #setRenderer} to register a renderer.
     *
     * @param context
     * @param attrs
     */
    public TestGLSurfaceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setDebugFlags(DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS);
        renderer=new DemoRenderer();
        this.setRenderer(renderer);
        cube=new Cube();
    }

    @Override
    public boolean onTouchEvent(final MotionEvent event){
        //由于DemoRenderer2对象运行在另一个线程中，这里采用跨线程的机制进行处理。使用queueEvent方法
        //当然也可以使用其他像Synchronized来进行UI线程和渲染线程进行通信。
        this.queueEvent(new Runnable() {

            @Override
            public void run() {

                //TODO:
                renderer.setColor(event.getX() / getWidth(), event.getY() / getHeight(), 1.0f);
            }
        });

        return true;
    }

    class DemoRenderer implements Renderer{
        private float mRed;
        private float mGreen;
        private float mBlue;


        private float[] mTriangleArray = {
                0f,1f,0f,
                -1f,-1f,0f,
                1f,-1f,0f
        };
        private FloatBuffer mTriangleBuffer;


        private float[] mColorArray={
                1f,0f,0f,1f,     //红
                0f,1f,0f,1f,     //绿
                0f,0f,1f,1f      //蓝
        };
        private FloatBuffer mColorBuffer;

        //正方形的四个顶点
        private FloatBuffer quateBuffer ;
        private float[] mQuateArray = {
                -1f, -1f, 0f,
                1f, -1f, 0f,
                -1f, 1f, 0f,
                1f, 1f, 0f
        };
        @Override
        public void onDrawFrame(GL10 gl) {
            //每帧都需要调用该方法进行绘制。绘制时通常先调用glClear来清空framebuffer。
            //然后调用OpenGL ES其他接口进行绘制
            gl.glClearColor(mRed, mGreen, mBlue, 1.0f);
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            cube.draw(gl);
           /* int[] _textureYUV=new int[3];
            gl.glGenTextures(3, _textureYUV,0);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, _textureYUV[0]);*/
            /**
             * target     指定目标纹理，这个值必须是GL_TEXTURE_2D。
             level       执行细节级别。0是最基本的图像级别，你表示第N级贴图细化级别。
             internalformat     指定纹理中的颜色组件，这个取值和后面的format取值必须相同。可选的值有
             GL_ALPHA,
             GL_RGB,
             GL_RGBA,
             GL_LUMINANCE,
             GL_LUMINANCE_ALPHA 等几种。
             width     指定纹理图像的宽度，必须是2的n次方。纹理图片至少要支持64个材质元素的宽度
             height     指定纹理图像的高度，必须是2的m次方。纹理图片至少要支持64个材质元素的高度
             border    指定边框的宽度。必须为0。
             format    像素数据的颜色格式，必须和internalformatt取值必须相同。可选的值有
             GL_ALPHA,
             GL_RGB,
             GL_RGBA,
             GL_LUMINANCE,
             GL_LUMINANCE_ALPHA 等几种。
             type        指定像素数据的数据类型。可以使用的值有
             GL_UNSIGNED_BYTE,
             GL_UNSIGNED_SHORT_5_6_5,
             GL_UNSIGNED_SHORT_4_4_4_4,
             GL_UNSIGNED_SHORT_5_5_5_1
             pixels      指定内存中指向图像数据的指针
             */
           /* gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_LUMINANCE, iFrameWidth, iFrameHeight, 0,
                    GL10.GL_LUMINANCE, GL10.GL_UNSIGNED_BYTE, y_pixels);

            gl.glBindTexture(GL10.GL_TEXTURE_2D, _textureYUV[1]);
            gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_LUMINANCE, iFrameWidth / 2,
                    iFrameHeight / 2, 0, GL10.GL_LUMINANCE, GL10.GL_UNSIGNED_BYTE, u_pixels);

            gl.glBindTexture(GL10.GL_TEXTURE_2D, _textureYUV[2]);
            gl.glTexImage2D(GL10.GL_TEXTURE_2D, 0, GL10.GL_LUMINANCE, iFrameWidth / 2,
                    iFrameHeight / 2, 0, GL10.GL_LUMINANCE, GL10.GL_UNSIGNED_BYTE, v_pixels);

            gl.glVertexAttribPointer(GL10.ATTRIB_VERTEX, 2, GL10.GL_FLOAT, 0, 0, squareVertices);
            gl.glEnableVertexAttribArray(GL10.ATTRIB_VERTEX);

            gl.glVertexAttribPointer(GL10.ATTRIB_TEXTURE, 2, GL10.GL_FLOAT, 0, 0, coordVertices);
            gl.glEnableVertexAttribArray(GL10.ATTRIB_TEXTURE);

            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);*/


            //使用数组作为颜色
            gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorBuffer);

            //绘制小三角形
            gl.glLoadIdentity();
            gl.glTranslatef(-1.5f, 0.0f, -6.0f);
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mTriangleBuffer);//数组指向三角形顶点buffer
            gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);
//      gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
            gl.glFinish();

            //绘制正方形
            gl.glLoadIdentity();
            gl.glTranslatef(1.5f, 0.0f, -6.0f);
//      gl.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
            // 设置顶点的位置数据
            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, quateBuffer);

            // 根据顶点数据绘制平面图形
            gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 3);
            gl.glFinish();

        }
        @Override
        public void onSurfaceChanged(GL10 gl, int w, int h) {
            //当surface的尺寸发生改变时，该方法被调用，。往往在这里设置ViewPort。或者Camara等。
            gl.glViewport(0, 0, w, h);

            float ratio = (float) w / h;
            gl.glMatrixMode(GL10.GL_PROJECTION);
            gl.glLoadIdentity();
            gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();
        }
        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // 该方法在渲染开始前调用，OpenGL ES的绘制上下文被重建时也会调用。
            //当Activity暂停时，绘制上下文会丢失，当Activity恢复时，绘制上下文会重建。

            //do nothing special
            // 设置背景颜色
            gl.glClearColor(1f, 1f, 1f, 0f);
            // 启用顶点数组（否则glDrawArrays不起作用）
           // gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);


            gl.glShadeModel(GL10.GL_SMOOTH);
            gl.glClearColor(1.0f, 1.0f, 1.0f, 0f);
            gl.glClearDepthf(1.0f);
            gl.glEnable(GL10.GL_DEPTH_TEST);
            gl.glDepthFunc(GL10.GL_LEQUAL);
            gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

            mTriangleBuffer = BufferUtil.floatToBuffer(mTriangleArray);
            mColorBuffer = BufferUtil.floatToBuffer(mColorArray);
            quateBuffer = BufferUtil.floatToBuffer(mQuateArray);
        }

        public void setColor(float r, float g, float b){
            this.mRed = r;
            this.mGreen = g;
            this.mBlue = b;
        }

    }
}
