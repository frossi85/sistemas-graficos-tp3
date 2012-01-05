package utilidades;

import objetosEscena.CintaTransportadora;
import objetosEscena.Dispenser;
import objetosEscena.Empaquetador;

public class CintaLlena implements ComportamientoProduccion {
	
	private CintaTransportadora cinta;
	private Dispenser disp;
	private Empaquetador empa;
	
	public CintaLlena(CintaTransportadora cinta,Dispenser disp,Empaquetador empa){
		this.cinta = cinta;
		this.disp = disp;
		this.empa = empa;
	}
	@Override
	public void producir() {
		this.empa.recibirBotella(this.cinta.entragarBotella());
		this.cinta.avanzarCinta();
		this.cinta.recibirBotella(this.disp.entregarBotella());
	}

}