package objetosEscena;

import java.util.Observable;

import utilidades.Dibujable;
import utilidades.LineaProduccion;

public class Rellenador extends Observable implements Dibujable {

	
	public void liberarLiquido(){}
	
	public Rellenador(LineaProduccion linea){
		addObserver(linea);
	}
	
	@Override
	public void dibujar() {
		// TODO Auto-generated method stub

	}

}
