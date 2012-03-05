package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GLAutoDrawable;

import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

public class Etiquetador extends Observable implements Dibujable,Animable {
	
	private float tiempoEtiquetado;
	private boolean terminoEtiquetado;
	private Vertice posicion;
	
	public Etiquetador(LineaProduccion linea, float tiempoEtiquetado){
		this.tiempoEtiquetado = tiempoEtiquetado;
		addObserver(linea);
		this.posicion = new Vertice(3f,0f,0f);
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
		
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}

}
