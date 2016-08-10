package com.gosuncn.test.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

/** 
 * A vertex shaded cube. 
 */  
class Cube  
{  
    public Cube()  
    {  
        int one = 0x10000;  
          
        //每一个顶点都是由x/y/z三个坐标表示的。对于一个立方体，有八个顶点。  
        //每个顶点的位置是先下到上，在每一层是由里到外逆时针方向标识  
        int vertices[] = {  
                -one, -one, -one,  
                one, -one, -one,  
                one,  one, -one,  
                -one,  one, -one,  
                -one, -one,  one,  
                one, -one,  one,  
                one,  one,  one,  
                -one,  one,  one,  
        };  
        //每个顶点的颜色由四个数字表示：RED/GREEN/BLUE/ALPHA,最后一个透明度是可选的  
        int colors[] = {  
                0,    0,    0,  one,  
                one,    0,    0,  one,  
                one,  one,    0,  one,  
                0,  one,    0,  one,  
                0,    0,  one,  one,  
                one,    0,  one,  one,  
                one,  one,  one,  one,  
                0,  one,  one,  one,  
        };  
        //这里有点复杂，每个数字对应于正方体中的每个特定的点。比如0代表立方体原点，就是立方体下面一层中最后面的那个点。  
        //那么0,4,5对应的就是立方体侧面上的一个三角，每个面都有两个三角组成。  
        //这样，整个立方体有六个面，就有12个三角面了。  
        //注意：点的排列顺序对于显示效果有很大的影响。比如：0,4,5和0,5,4就不一样。  
        //之所以会这样的原因是因为，调用了gl.glFrontFace(gl.GL_CW).  
        //这样我们需要以顺时针的顺序来指定可视的三角面。  
        byte indices[] = {  
                0, 4, 5,    0, 5, 1,  
                1, 5, 6,    1, 6, 2,  
                2, 6, 7,    2, 7, 3,  
                3, 7, 4,    3, 4, 0,  
                4, 7, 6,    4, 6, 5,  
                3, 0, 1,    3, 1, 2  
        };  
        //这里使用ByteBuffer来做缓冲处理。必须通过ByteBuffer.allocateDirect()方法来实例化ByteBuffer对象  
        //这些buffer必须放到本地堆栈中，以使垃圾回收器不能移除它们  
        //这些缓冲将作为参数传入到gl*Pointer()方法中  
        //对于不同原数据类型的Buffers需要将byte设置成对应的顺序  
        //int类型的是每个点式四个字节。  
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length*4);  
        vbb.order(ByteOrder.nativeOrder());  
        mVertexBuffer = vbb.asIntBuffer();  
        mVertexBuffer.put(vertices);  
        mVertexBuffer.position(0);  
        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length*4);  
        cbb.order(ByteOrder.nativeOrder());  
        mColorBuffer = cbb.asIntBuffer();  
        mColorBuffer.put(colors);  
        mColorBuffer.position(0);  
        mIndexBuffer = ByteBuffer.allocateDirect(indices.length);  
        mIndexBuffer.put(indices);  
        mIndexBuffer.position(0);  
    }  
    //让Cube来绘制自己，传入GL10g  
    //GL10里包含很多绘制3D图刑的接口  
    public void draw(GL10 gl)  
    {  
        gl.glFrontFace(gl.GL_CW);  
        gl.glVertexPointer(3, gl.GL_FIXED, 0, mVertexBuffer);  
        gl.glColorPointer(4, gl.GL_FIXED, 0, mColorBuffer);  
        gl.glDrawElements(gl.GL_TRIANGLES, 36, gl.GL_UNSIGNED_BYTE, mIndexBuffer);  
    }  
    private IntBuffer   mVertexBuffer;  
    private IntBuffer   mColorBuffer;  
    private ByteBuffer  mIndexBuffer;  
}  