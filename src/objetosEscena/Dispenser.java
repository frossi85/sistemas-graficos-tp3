package objetosEscena;

import java.util.Observable;

import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

public class Dispenser extends Observable implements Dibujable {
	
	private Vertice posicion;
	
	public Dispenser(LineaProduccion linea){
	addObserver(linea);
	this.posicion = new Vertice(0f,0f,0f);
	}
	
	public Botella entregarBotella(){
		return new Botella();
	}
	
	public Vertice getPosicon(){
		return this.posicion;
	}
	
	@Override
	public void dibujar() {
		System.out.println("Se dibujo dispenser");

	}

}
