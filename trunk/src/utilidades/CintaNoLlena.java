package utilidades;


import objetosEscena.CintaTransportadora;
import objetosEscena.Dispenser;

public class CintaNoLlena implements ComportamientoProduccion {	// modela comportamiento de la produccion cuando la cinta transportadora aun no se lleno
	private CintaTransportadora cinta;
	private Dispenser disp;
	
	public CintaNoLlena(CintaTransportadora cinta,Dispenser disp){
		this.cinta = cinta;
		this.disp = disp;
	}
	
	public void producir() {
		cinta.avanzarCinta();
		cinta.recibirBotella(disp.entregarBotella());

	}

}
