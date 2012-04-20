package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

import shader.ManejoShaders2;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

public class Dispenser extends Observable implements Dibujable {
	
	private Vertice posicion;
	private GLUT glut;
	private float alto = 2f;
	private float ancho = 1f;
	private float largo = 1.5f;
	private float anchoAbertura = ancho/4f;
	private float altoAbertura = alto/4f;
	private float profundidadAbertura = largo/8f;
	private ManejoShaders2 shader;
	GLU glu;
	GLAutoDrawable gLDrawable;
	
	
	public Dispenser(LineaProduccion linea, ManejoShaders2 shader, GLUT glut, GLU glu, GLAutoDrawable gLDrawable){
	addObserver(linea);
	this.glu = glu;
	this.glut = glut;
	this.gLDrawable = gLDrawable;
	this.posicion = new Vertice(-0.25f,0f,0f);
	this.shader = shader;
	}
	
	public Botella entregarBotella(){
		return new Botella(this.shader, glut, glu, gLDrawable);
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
