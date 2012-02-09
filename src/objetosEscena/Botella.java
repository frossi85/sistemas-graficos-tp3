package objetosEscena;
import java.util.Observable;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;
import javax.media.opengl.glu.GLUquadric;

import com.jogamp.opengl.util.gl2.GLUT;

import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;


public class Botella  implements Dibujable,Animable {
	private boolean lleno;
	private boolean etiquetado;
	private float porcentajeLlenado;
	private static float altura = 1.0f;
	private Vertice posicion;
	
	
	public Botella(){
		this.lleno = false;
		this.etiquetado = false;
		this.posicion = new Vertice(0f,0f,0f);
	}

	public void setPorcentajelLlenado(float porcentaje){
		this.porcentajeLlenado = porcentaje;
	}
	
	public void setPosicion(Vertice vert){
		this.posicion.setX(vert.getX());
		this.posicion.setY(vert.getY());
		this.posicion.setZ(vert.getZ());
	}
	
	public Vertice getPosicion(){
		return this.posicion;
	}
	
	public boolean estaLleno() {
		return lleno;
	}

	public void setLleno(boolean lleno) {
		this.lleno = lleno;
	}

	public boolean estaEtiquetado() {
		return etiquetado;
	}

	public void setEtiquetado(boolean etiquetado) {
		this.etiquetado = etiquetado;
	}

	public float getPorcentajeLlenado() {
		return porcentajeLlenado;
	}

	public void setPorcentajeLlenado(float porcentajeLlenado) {
		this.porcentajeLlenado = porcentajeLlenado;
	}

	public float getAltura() {
		return altura;
	}

	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		System.out.println("Se dibujo botella");
		//GL2 gl = gLDrawable.getGL().getGL2();
		GLU glu = new GLU(); 
		GLUquadric qobj0 = glu.gluNewQuadric();
		
		glu.gluQuadricDrawStyle(qobj0, GLU.GLU_FILL);
		glu.gluQuadricNormals(qobj0, GLU.GLU_SMOOTH);
		glu.gluCylinder(qobj0, 0.5f, 0.5f, this.altura, 15, 5);
		//glu.gluDeleteQuadric(qobj0);
			
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}
}
