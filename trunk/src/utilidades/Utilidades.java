package utilidades;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.media.opengl.GL2;

public class Utilidades {
	
    public static FloatBuffer makeFloatBuffer(float[] arr) {
	    ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
	    bb.order(ByteOrder.nativeOrder());
	    FloatBuffer fb = bb.asFloatBuffer();
	    fb.put(arr);
	    fb.position(0);
	    return fb;
    }
    
    public static void glVertex(Vertice p)
    {
    	GLProvider.getGL2().glVertex3f(p.getX(), p.getY(), p.getZ());
    }
    
    public static void glNormal(Vertice p)
    {
    	GLProvider.getGL2().glNormal3f(p.getX(), p.getY(), p.getZ());
    }
}
