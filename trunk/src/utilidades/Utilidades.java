package utilidades;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

public class Utilidades {
	
    public static FloatBuffer makeFloatBuffer(float[] arr) {
	    ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
	    bb.order(ByteOrder.nativeOrder());
	    FloatBuffer fb = bb.asFloatBuffer();
	    fb.put(arr);
	    fb.position(0);
	    return fb;
    }
}
