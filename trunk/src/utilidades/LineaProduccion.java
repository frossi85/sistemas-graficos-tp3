package utilidades;

import objetosEscena.CintaTransportadora;
import objetosEscena.Dispenser;
import objetosEscena.Empaquetador;
import objetosEscena.Etiquetador;
import objetosEscena.Rampa;
import objetosEscena.Rellenador;

import java.util.Observable;
import java.util.Observer;

import javax.media.opengl.GLAutoDrawable;

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
		System.out.println("tiempo actual: " + this.timer);
		
	}
	
	public void ejecutarAnimacion(){
		if(cinta.buscarPosicion(this.etiquetador.getPosicion().getX())){
			this.cinta.detenerCinta();
			this.etiquetador.animar();
			System.out.println("animacion de etiquetador");
			this.cinta.activarCinta();
		}
		if (cinta.buscarPosicion(this.rellenador.getPosicion().getX())){
			this.cinta.detenerCinta();
			this.rellenador.animar();
			System.out.println("animacion de rellenador");
			this.cinta.activarCinta();
		}
	}
	
	public void producir(){  // TODO este metodo mueve toda la produccion
		if (! this.cinta.estaLlenaDeBotellas()){
			if(cinta.estaAvanzando()){
				cinta.avanzarCinta();
				cinta.recibirBotella(this.expededoraBotellas.entregarBotella());
				System.out.println("dispenser entrego botella a cinta");
			}
		}	
			else{
				if(cinta.estaAvanzando()){
					this.empaquetador.recibirBotella(this.cinta.entragarBotella());
					System.out.println("cinta entrego botella a empaquetador");
					this.cinta.avanzarCinta();
					this.cinta.recibirBotella(this.expededoraBotellas.entregarBotella());
					System.out.println("dispenser entrego botella a cinta");
				}
			}		
		ejecutarAnimacion();
		System.out.println("num de bot en cinta: " + this.cinta.getNumeroDeBotellas());
	}
	
	public void dibujar(GLAutoDrawable gLDrawable){
		this.expededoraBotellas.dibujar(gLDrawable);
		this.cinta.dibujar(gLDrawable);
		this.etiquetador.dibujar(gLDrawable);
		this.rellenador.dibujar(gLDrawable);
		this.empaquetador.dibujar(gLDrawable);
		this.rampa.dibujar(gLDrawable);
	}
	
	public void actualizar(){
		this.avanzarTiempo();
		this.producir();
		
	}
	@Override
	public void update(Observable o, Object arg) {
		
		}
		
}
	

	


