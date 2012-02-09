package objetosEscena;

import java.util.Observable;

import javax.media.opengl.GLAutoDrawable;

import utilidades.Dibujable;
import utilidades.LineaProduccion;

public class Empaquetador extends Observable implements Dibujable {

	int cantidadBotellasRecibidas;
	public static int CAPACIDAD_BOTELLAS = 4;
	
	public Empaquetador(LineaProduccion linea,Rampa rampa){
		this.cantidadBotellasRecibidas = 0;
		addObserver(linea);
		addObserver(rampa);
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
		System.out.println("Se dibujo empaquetador");

	}

}
