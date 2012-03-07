package objetosEscena;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import utilidades.Dibujable;

public class Piso implements Dibujable {
		private float ancho = 20f;
		private float largo = 10f;
		private float coordZ = 4;	// a que altura de la escena esta
		private float proporcionLargo;
		private float proporcionAncho;
		private GLUT glut = new GLUT();
	
	public Piso(){
		if(ancho >= largo){
			this.proporcionAncho = ancho/largo;
			this.proporcionLargo = largo/ancho;
		}
		
		else {
			this.proporcionAncho = largo/ancho;
			this.proporcionLargo = ancho/largo;
		}
	}
	
	public void dibujar(GLAutoDrawable gLDrawable) {
		// TODO Auto-generated method stub
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glPushMatrix();
			for(float i = 0f; i < largo; i+= proporcionLargo){
				gl.glBegin(GL.GL_LINES);
	  			gl.glVertex3f(ancho/2, largo/2 - i, coordZ);
	  			gl.glVertex3f(-ancho/2, largo/2 - i, coordZ);
	  			gl.glEnd();
			}
			
			for(float i = 0f; i < ancho; i+= proporcionAncho){
				gl.glBegin(GL.GL_LINES);
	  			gl.glVertex3f(ancho/2 - i, -largo/2, coordZ);
	  			gl.glVertex3f(ancho/2 - i, largo/2 , coordZ);
	  			gl.glEnd();
			}
			
		gl.glPopMatrix();	
	}

}
