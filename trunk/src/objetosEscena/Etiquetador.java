package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;

import com.jogamp.opengl.util.gl2.GLUT;

import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Objeto3D;
import utilidades.Vertice;

import model.ModelFactory;
import model.ModelLoadException;
import model.iModel3DRenderer;
import model.examples.DisplayListRenderer;
import model.geometry.Model;

public class Etiquetador extends Objeto3D implements Animable {
	
	private float tiempoEtiquetado;
	private boolean terminoEtiquetado;
	
	public Etiquetador(LineaProduccion linea, float tiempoEtiquetado){
		super("model/examples/models/obj/etiquetadora.obj");
		this.tiempoEtiquetado = tiempoEtiquetado;
		addObserver(linea);
		this.posicion = new Vertice(0f,0f,0f);
	}
	
	public void etiquetar(){}
	
	//TODO: No se si es necesario
	public boolean terminoDeEtiquetar(){return terminoEtiquetado;}
	
	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}
}
