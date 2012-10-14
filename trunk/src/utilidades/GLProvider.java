package utilidades;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

public class GLProvider {
	
	private static GLAutoDrawable PROVIDER = null;
	 
    // Private constructor suppresses 
    private GLProvider() {
    	
    }
    
    public static void SetUp(GLAutoDrawable gLAutoDrawable) {
    	PROVIDER = gLAutoDrawable;
    }
    
	
	public static GL2 getGL2()
	{
		return PROVIDER.getGL().getGL2();
	}

}
