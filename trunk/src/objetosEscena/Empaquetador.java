package objetosEscena;

import utilidades.Dibujable;

public class Empaquetador implements Dibujable {

	int cantidadBotellasRecibidas;
	
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
