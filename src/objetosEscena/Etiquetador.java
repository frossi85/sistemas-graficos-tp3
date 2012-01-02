package objetosEscena;

import java.util.Observable;

import utilidades.Dibujable;
import utilidades.LineaProduccion;

public class Etiquetador extends Observable implements Dibujable {
	
	private float tiempoEtiquetado;
	private boolean terminoEtiquetado;
	
	public Etiquetador(LineaProduccion linea, float tiempoEtiquetado){
		this.tiempoEtiquetado = tiempoEtiquetado;
		addObserver(linea);
	}
	
	public void etiquetar(){}
	
	//TODO: No se si es necesario
	public boolean terminoDeEtiquetar(){return terminoEtiquetado;}

	@Override
	public void dibujar() {
		// TODO Auto-generated method stub
		
	}

}
