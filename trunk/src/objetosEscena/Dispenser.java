package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

public class Dispenser extends Observable implements Dibujable {
	
	private Vertice posicion;
	private GLUT glut = new GLUT();
	
	public Dispenser(LineaProduccion linea){
	addObserver(linea);
	this.posicion = new Vertice(-0.25f,0f,0f);
	}
	
	public Botella entregarBotella(){
		return new Botella();
	}
	
	public Vertice getPosicon(){
		return this.posicion;
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		//System.out.println("Se dibujo dispenser");
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glPushMatrix();
			gl.glTranslatef(this.posicion.getX(), 0.0f, 0.0f);
			glut.glutSolidCube(0.5f);
		gl.glPopMatrix();	

	}

}
