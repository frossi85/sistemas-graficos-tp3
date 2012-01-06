package objetosEscena;

import java.util.Observable;

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
	public void dibujar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}

}
