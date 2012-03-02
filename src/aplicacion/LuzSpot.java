package aplicacion;

import javax.media.opengl.GL2;

import utilidades.Utilidades;

public class LuzSpot {
	
	//private Posicion posicionLuz;
	//private Direccion direccionLuz;
	//private double anguloAperturaLuz; //Cutoff angle
	//private Color colorLuz;
	private GL2 gl;
	private static int contadorLuces = 0;
	
    // Private constructor prevents instantiation from other classes
	public LuzSpot() { }
	
	//{
		//this.gl = gl;
		//contadorLuces++;
	//}
	
    /**
     * SingletonHolder is loaded on the first execution of Singleton.getInstance() 
     * or the first access to SingletonHolder.INSTANCE, not before.
     */
     private static class SingletonHolder { 
             public static final LuzSpot instance = new LuzSpot();
     }

     public static LuzSpot getLuzSpot(GL2 gl, float [] colorRGB, float angulo, float posicion, float direccion) {
    	 
    	 LuzSpot luz = SingletonHolder.instance;
    	 
    	 
         // prepare spotlight
         float spot_ambient[] = {10.2f,10.2f,10.2f,1.0f };  //Blanco
         float spot_diffuse[] =   {50.8f,0.0f,0.0f,1.0f };  //colorRGB
         float spot_specular[] =  {50.8f,0.0f,0.0f,1.0f };
    	 float spotlightDirection[] = {1.0f,1.0f,-6.0f}; // spot light direction
    	 float spotlightPosition[] = {1.0f,1.0f,-6.0f}; // spot light direction
 
         
    	 gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_AMBIENT, spot_ambient, 0);        // Set Light Ambience
         gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_DIFFUSE, spot_diffuse, 0);        // Set Light Diffuse
         gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_SPECULAR, spot_specular, 0);
    	 

		 gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_POSITION, Utilidades.makeFloatBuffer(spotlightPosition));
		 gl.glLightf(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_SPOT_CUTOFF, angulo); // spot light cut-off
    	 gl.glLightfv(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_SPOT_DIRECTION, Utilidades.makeFloatBuffer(spotlightDirection));
    	 gl.glLightf(GL2.GL_LIGHT0 + contadorLuces, GL2.GL_SPOT_EXPONENT, 50.0f); // spot light exponent
         
    	 // "smoothing" the border of the lightcone
         // change this for effect
         gl.glMaterialfv(GL2.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE, new float[] {0.7f,0.7f,1}, 0 );
    	 
    	 
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
