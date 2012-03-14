package objetosEscena;
import java.util.ArrayList;
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
import utilidades.PuntoDeControl;
import utilidades.SuperficieDeRevolucion;
import utilidades.Vertice;




public class Botella  implements Dibujable,Animable {
	private boolean lleno;
	private boolean etiquetado;
	private float porcentajeLlenado;
	private static float altura = 1.0f;
	private SuperficieDeRevolucion sup;
	private Vertice posicion;	// posicion relativa a la cinta transportadora
	private GLUT glut = new GLUT();
	
	
	public Botella(){
		this.lleno = false;
		this.etiquetado = false;
		this.posicion = new Vertice(0f,0f,0f);
		ArrayList<PuntoDeControl>list = new ArrayList<PuntoDeControl>();
				
		//ult1
		PuntoDeControl punto1 = new PuntoDeControl(1.7f,3f);
		PuntoDeControl punto2 = new PuntoDeControl(1f,2f);
		PuntoDeControl punto3 = new PuntoDeControl(0.4f,1.5f);
		PuntoDeControl punto4 = new PuntoDeControl(0.4f,0f);
		//ult2
		PuntoDeControl punto5 = new PuntoDeControl(3f,20f);
		PuntoDeControl punto6 = new PuntoDeControl(1f,9f);
		PuntoDeControl punto7 = new PuntoDeControl(2f,4.5f);
		PuntoDeControl punto8 = new PuntoDeControl(1.7f,3f);
		
		list.add(punto1);
		list.add(punto2);
		list.add(punto3);
		list.add(punto4);
		
		list.add(punto5);
		list.add(punto6);
		list.add(punto7);
		list.add(punto8);
		
		this.sup = new SuperficieDeRevolucion(list);
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
		//System.out.println("Se dibujo botella");
		final GL2 gl = gLDrawable.getGL().getGL2();
		
		gl.glPushMatrix();
			gl.glTranslatef(this.posicion.getX(), 0.0f, 0.0f);
			glut.glutSolidCylinder(0.1f, 0.5f, 20, 20);
		gl.glPopMatrix();	

//		this.sup.dibujar(gLDrawable);

			
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}
}
