package utilidades;

import objetosEscena.CintaTransportadora;
import objetosEscena.Dispenser;
import objetosEscena.Empaquetador;
import objetosEscena.Etiquetador;
import objetosEscena.Rampa;
import objetosEscena.Rellenador;

import java.util.Observable;
import java.util.Observer;

public class LineaProduccion implements Observer{
	
	private Dispenser expededoraBotellas;
	private CintaTransportadora cinta;
	private Etiquetador etiquetador;
	private Rellenador rellenador;
	private Empaquetador empaquetador;
	private Rampa rampa;
	private float timer = 0.0f;
	private static float AVANCE_TIEMPO = 0.1f;
	private static float VELOCIDAD_CINTA = 5f;
	private static int CAPACIDAD_CINTA = 10;
	private static float TIEMPO_ETIQUETADO = 3f;
	private static float AVANCE_BOTELLAS = VELOCIDAD_CINTA*AVANCE_TIEMPO;  //distancia = tiempo*velocidad
	
	
	public LineaProduccion(){
		this.cinta = new CintaTransportadora(CAPACIDAD_CINTA, this, VELOCIDAD_CINTA, AVANCE_BOTELLAS);
		this.rampa = new Rampa(this);
		this.empaquetador = new Empaquetador(this,rampa);
		this.etiquetador = new Etiquetador(this, TIEMPO_ETIQUETADO);
		this.expededoraBotellas = new Dispenser(this);
		this.rellenador = new Rellenador(this);
	}
	
	public void avanzarTiempo(){
		this.timer += AVANCE_TIEMPO;
		
	}
	
	public void Producir(){  // TODO este metodo mueve toda la produccion
		if(!this.cinta.estaLlenaDeBotellas()){
			this.cinta.recibirBotella(this.expededoraBotellas.entregarBotella());
			this.cinta.avanzarCinta();
			
		}
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		
	}
	

	

}
