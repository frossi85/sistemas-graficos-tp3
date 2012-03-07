package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

public class Etiquetador extends Observable implements Dibujable,Animable {
	
	private float tiempoEtiquetado;
	private boolean terminoEtiquetado;
	private Vertice posicion;
	private GLUT glut = new GLUT();
	
	public Etiquetador(LineaProduccion linea, float tiempoEtiquetado){
		this.tiempoEtiquetado = tiempoEtiquetado;
		addObserver(linea);
		this.posicion = new Vertice(6f,0f,0f);
	}
	
	public void etiquetar(){}
	
	//TODO: No se si es necesario
	public boolean terminoDeEtiquetar(){return terminoEtiquetado;}
	
	public Vertice getPosicion(){
		return this.posicion;
	}
	
	public void setPosicion(Vertice vert){
		this.posicion = vert;
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		//System.out.println("Se dibujo etiquetador");
		final GL2 gl = gLDrawable.getGL().getGL2();
		gl.glPushMatrix();
			gl.glTranslatef(this.posicion.getX(), 1f, 0.0f);
			glut.glutSolidCube(0.9f);
		gl.glPopMatrix();
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}

}
