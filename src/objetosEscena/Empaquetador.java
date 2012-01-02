package objetosEscena;

import java.util.Observable;

import utilidades.Dibujable;
import utilidades.LineaProduccion;

public class Empaquetador extends Observable implements Dibujable {

	int cantidadBotellasRecibidas;
	
	public Empaquetador(LineaProduccion linea,Rampa rampa){
		addObserver(linea);
		addObserver(rampa);
	}
	public void recibirBotella(Botella botella){
		this.cantidadBotellasRecibidas++;
		
		if(this.cantidadBotellasRecibidas == 4){
			//genero caja armada de botellas y se la paso a la rampa
			this.cantidadBotellasRecibidas = 0;
		}
	}
	
	@Override
	public void dibujar() {
		// TODO Auto-generated method stub

	}

}
