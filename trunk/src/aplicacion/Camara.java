package aplicacion;

import javax.media.opengl.GL2;
import javax.media.opengl.glu.GLU;

public class Camara {
    private double xPos;
    private double yPos;
    private double zPos;
    
    private double pitch;
    private double yaw;
    
    GL2 gl;
    GLU glu;
       
    private Camara()
    {
        xPos = 0;
        yPos = 0;
        zPos = 0;
        
        pitch = 0;
        yaw = 0;
    }
    
    public Camara(GL2 gl, GLU glu, double xPos, double yPos, double zPos, double pitch, double yaw)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
        
        this.pitch = pitch;
        this.yaw = yaw;
        
        this.gl = gl;
        this.glu = glu;
        
		glu.gluLookAt(0.0f, 0.0f, -1f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);
        
		//glu.gluLookAt(eye[0], eye[1] , eye[2],
			//	  at[0], at[1], at[2],
 				//  up[0], up[1], up[2]);
		
		// Variables que controlan la ubicaci�n de la c�mara en la Escena 3D
//	    //private float eye[] =  {0.0f, 0.0f, 4.0f};
//	    //private float at[]  = { 0.0f,  0.0f, -1.0f};
//		private float at[]  = { 0.0f,  1.0f, -1.0f};
//	    private float up[]  = { 0.0f,  1.0f, 0.0f};
    }
    
    public void actualizarPosicion(double xPos, double yPos, double zPos)
    {
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }
      
    // Avanza la camara de acuerdo su pitch, yaw y la magnitud.
    
    public void avanzar(double magnitud)
    {
        double xActual = this.xPos;
        double yActual = this.yPos;
        double zActual = this.zPos;
        
        // Coordenadas Esfericas
        double xMovimiento = magnitud * Math.cos(pitch) * Math.cos(yaw); 
        double yMovimiento = magnitud * Math.sin(pitch);
        double zMovimiento = magnitud * Math.cos(pitch) * Math.sin(yaw);
              
        double xNew = xActual + xMovimiento;
        double yNew = yActual + yMovimiento;
        double zNew = zActual + zMovimiento;
        
        actualizarPosicion(xNew, yNew, zNew);
    }
    
    public void desplazarIzquierda(double magnitud)
    {
        double pitchTemp = pitch;
        pitch = 0;
        yaw = yaw - (0.5 * Math.PI);
        avanzar(magnitud);

        pitch = pitchTemp;
        yaw = yaw + (0.5 * Math.PI);
    }
    
    public void desplazarDerecha(double magnitud)
    {
        double pitchTemp = pitch;
        pitch = 0;

        yaw = yaw + (0.5 * Math.PI);
        avanzar(magnitud);
        yaw = yaw - (0.5 * Math.PI);

        pitch = pitchTemp;
    }   
    
    /* -------Getters--------- */
    
    public double getXPos()
    {
        return xPos;
    }
    
    public double getYPos()
    {
        return yPos;
    }
    
    public double getZPos()
    {
        return zPos;
    }
    
    public double getPitch()
    {
        return pitch;
    }
    
    public double getYaw()
    {
        return yaw;
    }
    
    /* --------------------------- */
    
    /* -------------- Comandos Pitch y Yaw --------------- */
    
    public void pitchArriba(double cantidad)
    {
        this.pitch += cantidad;
    }
    
    public void pitchAbajo(double cantidad)
    {
        this.pitch -= cantidad;
    }
    
    public void yawDerecha(double cantidad)
    {
        this.yaw += cantidad;
    }
    
    public void yawIzquierda(double cantidad)
    {
        this.yaw -= cantidad;
    }
    
    /* ---------------------------------------------------- */
    
    public void render()
    {
  		// Transformaciones de la camara para rotar y mover los objetos alrededor de ella.
  		gl.glRotatef((float) pitch, 1, 0, 0);
  		gl.glRotatef((float) yaw, 0, 1, 0);
  		gl.glTranslated(-xPos, -yPos, -zPos);
    }
    

}
