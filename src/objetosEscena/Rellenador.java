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
		this.posicion = new Vertice(2f,0f,0f);
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
		System.out.println("Se dibujo rellenador");

	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}

}
