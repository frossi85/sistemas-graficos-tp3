package objetosEscena;

import java.util.Observable;

import utilidades.Dibujable;
import utilidades.LineaProduccion;

public class Dispenser extends Observable implements Dibujable {

	public Dispenser(LineaProduccion linea){
	addObserver(linea);
	}
	
	public Botella entregarBotella(){
		return new Botella(null);
	}
	
	@Override
	public void dibujar() {
		System.out.println("Se dibujo dispenser");

	}

}
