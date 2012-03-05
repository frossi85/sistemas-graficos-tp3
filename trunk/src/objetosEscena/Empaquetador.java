package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GLAutoDrawable;

import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

public class Empaquetador extends Observable implements Dibujable {

	int cantidadBotellasRecibidas;
	private Vertice posicion;
	public static int CAPACIDAD_BOTELLAS = 4;
	
	public Empaquetador(LineaProduccion linea,Rampa rampa){
		this.cantidadBotellasRecibidas = 0;
		this.posicion = new Vertice(9f,0f,0f);
		addObserver(linea);
		addObserver(rampa);
	}
	
	public Vertice getPosicion(){
		return this.posicion;
	}
	
	public void setPosicion(Vertice vert){
		this.posicion = vert;
	}
	
	public void recibirBotella(Botella botella){	// cuando recibo botellas notifico de cambio a observadores
		this.cantidadBotellasRecibidas++;
		if(this.cantidadBotellasRecibidas == 4){
			setChanged();
	        notifyObservers();
	        clearChanged();
			this.cantidadBotellasRecibidas = 0;
			
		}
	}
	
	public int getCantidadBotellasRecibidas(){
		return this.cantidadBotellasRecibidas;
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		//System.out.println("Se dibujo empaquetador");

	}

}
