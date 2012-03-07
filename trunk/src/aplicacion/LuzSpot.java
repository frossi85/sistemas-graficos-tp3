package aplicacion;

import javax.media.opengl.GL2;

import utilidades.Utilidades;

public class LuzSpot {
	private float [] colorRGB;
	private float [] difusa;
	private float [] especular;
	private float cutoff;
	private float exponente;
	private float [] posicion;
	private float [] direccion;
	
	private GL2 gl;
	private static int contadorLuces = 0;
	
    // Private constructor prevents instantiation from other classes
	public LuzSpot() { }
	
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final LuzSpot instance = new LuzSpot();
     }

     public static LuzSpot getLuzSpot(GL2 gl, float [] colorRGB, float [] difusa, float [] especular, float cutoff, float exponente, float [] posicion, float [] direccion) {
    	 
    	 LuzSpot luz = SingletonHolder.instance;
    	 
    	 //Setteo propiedades de la luz
    	 luz.gl = gl;
    	 luz.colorRGB = colorRGB;
    	 

         // prepare spotlight         
    	 gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_AMBIENT, colorRGB, 0);        // Set Light Ambience
         gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_DIFFUSE, difusa, 0);        // Set Light Diffuse
         gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_SPECULAR, especular, 0);
    	 

		 gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_POSITION, Utilidades.makeFloatBuffer(posicion));
		 gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_SPOT_DIRECTION, Utilidades.makeFloatBuffer(direccion));
		 gl.glLightf(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_SPOT_CUTOFF, cutoff); // spot light cut-off
    	 gl.glLightf(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_SPOT_EXPONENT, exponente); // spot light exponent
         
    	 // "smoothing" the border of the lightcone
         // change this for effect
         //gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, new float[] {0.7f,0.7f,1}, 0 );
    	 
    	 
    	 // Enable Lighting
    	 /*
    	         gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, MatAmb, 0);         // Set Material Ambience
    	         gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, MatDif, 0);         // Set Material Diffuse
    	         gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, MatSpc, 0);        // Set Material Specular
    	         gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, MatShn, 0);       // Set Material Shininess
    	         
    	 */    	 
         
    	 gl.glEnable(GL2.GL_LIGHT0 + contadorLuces);
    	 
    	 contadorLuces++;
    	 return luz;
     }
	
	
	//Implementar el modelo de Phong con texturas y fuentes de luz tipo spot (la intensidad de luz
	//decae desde el eje central hacia afuera. En la figura hay 6 spots ubicados en el techo del
	//ambiente.

	public void setRGBColor(float r, float g, float b)
	{	
		
	}
	
	public void setAnguloApertura(float angulo)
	{
		//this.gl = gl.glLightf(GL_LIGHT0, GL_SPOT_CUTOFF, 30.0f);
		
		
	}
	
	public void setPosicion(float posicion)
	{
		
		
	}
	


}
