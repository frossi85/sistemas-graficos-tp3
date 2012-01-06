package objetosEscena;

import java.util.Observable;

import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

public class Rellenador extends Observable implements Dibujable,Animable {

	private Vertice posicion;
	public void liberarLiquido(){}
	
	public Rellenador(LineaProduccion linea){
		addObserver(linea);
	}
	
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
