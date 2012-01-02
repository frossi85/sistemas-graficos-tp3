package objetosEscena;

import java.util.Observable;

import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

public class Rellenador extends Observable implements Dibujable {

	private Vertice posicion;
	public void liberarLiquido(){}
	
	public Rellenador(LineaProduccion linea){
		addObserver(linea);
	}
	
	@Override
	public void dibujar() {
		// TODO Auto-generated method stub

	}

}
