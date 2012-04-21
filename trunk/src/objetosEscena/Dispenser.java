package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GL;
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
	private float separacionAberturaX = ancho/6;	// alejamiento de la abertura con respecto al borde del dispenser
	private float separacionAberturaY = largo/4;	// alejamiento de la abertura con respecto a la base del dispenser
	
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
			gl.glScalef(2, 2, 2);
			gl.glTranslatef(this.posicion.getX(), 0.0f, 0.0f);
			//glut.glutSolidCube(0.5f);
			gl.glPushMatrix();	// dibujo abertura
				gl.glTranslated(separacionAberturaX, separacionAberturaY,0);
				
				// PARTE IZQUIERDA
				gl.glBegin(GL.GL_LINE_STRIP);
				gl.glVertex3f(0, 0,0);
				gl.glVertex3f(0, 0, -profundidadAbertura);
				gl.glVertex3f(0, altoAbertura,-profundidadAbertura);
				gl.glVertex3f(0, altoAbertura,0);
				gl.glVertex3f(0, 0,0);
				gl.glEnd();
				
				//PARTE SUPERIOR
				gl.glBegin(GL.GL_LINE_STRIP);
				gl.glVertex3f(0, altoAbertura,-profundidadAbertura);
				gl.glVertex3f(0, altoAbertura,0);
				gl.glVertex3f(anchoAbertura, altoAbertura,0);
				gl.glVertex3f(anchoAbertura, altoAbertura,-profundidadAbertura);
				gl.glVertex3f(0, altoAbertura,-profundidadAbertura);
				gl.glEnd();
				
				//PARTE DERECHA
				gl.glBegin(GL.GL_LINE_STRIP);
				gl.glVertex3f(anchoAbertura, 0,0);
				gl.glVertex3f(anchoAbertura, 0, -profundidadAbertura);
				gl.glVertex3f(anchoAbertura, altoAbertura,-profundidadAbertura);
				gl.glVertex3f(anchoAbertura, altoAbertura,0);
				gl.glVertex3f(anchoAbertura, 0,0);
				gl.glEnd();
				
				//PARTE INFERIOR
				gl.glBegin(GL.GL_LINE_STRIP);
				gl.glVertex3f(0, 0,-profundidadAbertura);
				gl.glVertex3f(0, 0,0);
				gl.glVertex3f(anchoAbertura, 0,0);
				gl.glVertex3f(anchoAbertura, 0,-profundidadAbertura);
				gl.glVertex3f(0, 0,-profundidadAbertura);
				gl.glEnd();
			gl.glPopMatrix();
			
		gl.glPopMatrix();	

	}

}
