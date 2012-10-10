package utilidades;

import model.iModel3DRenderer;
import model.examples.DisplayListRenderer;
import objetosEscena.CintaTransportadora;
import objetosEscena.Dispenser;
import objetosEscena.Empaquetador;
import objetosEscena.Etiquetador;
import objetosEscena.Rampa;
import objetosEscena.Rellenador;

import java.util.Observable;
import java.util.Observer;

import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

import shader.ManejoShaders2;
import shader.ManejoShadersMejorado;

public class LineaProduccion implements Observer{
	
	private Dispenser expededoraBotellas;
	private CintaTransportadora cinta;
	private Etiquetador etiquetador;
	private Rellenador rellenador;
	private Empaquetador empaquetador;
	private Rampa rampa;
	private float timer = 0.0f;
	private float timerBotellas = 0.0f;	// hace cuanto salio la ultima botella del dispenser 
	private static float AVANCE_TIEMPO = 0.1f;
	private static float 	TIEMPO_ENTRE_BOTELLAS = 1f;	// tiempo de demora entre que sale una botella del dispenser a otra
	private static float VELOCIDAD_CINTA = 1f;
	private static int CAPACIDAD_CINTA = 10;
	private static float TIEMPO_ETIQUETADO = 3f;
	private static float AVANCE_BOTELLAS = VELOCIDAD_CINTA*AVANCE_TIEMPO;  //distancia = tiempo*velocidad
    private iModel3DRenderer modelRenderer;
    private GLUT glut = new GLUT();
	
	public LineaProduccion(ManejoShadersMejorado shader, GLUT glut, GLU glu, GLAutoDrawable gLDrawable){
		//HAcer singleton de la conf del model rederer asi no se la paso a todos los constructores
		//O mejor un factory que me devuelva toda la instancia creada
		// Get an instance of the display list renderer a renderer
        modelRenderer = DisplayListRenderer.getInstance();
        // Turn on debugging
        modelRenderer.debug(true);
		
		this.cinta = new CintaTransportadora(CAPACIDAD_CINTA, this, VELOCIDAD_CINTA, AVANCE_BOTELLAS);
		this.rampa = new Rampa(this);
		this.empaquetador = new Empaquetador(this,rampa, modelRenderer);
		this.etiquetador = new Etiquetador(this, modelRenderer, TIEMPO_ETIQUETADO);
		this.expededoraBotellas = new Dispenser(this, modelRenderer, shader, glut, glu, gLDrawable);
		this.rellenador = new Rellenador(this, modelRenderer);
	}
	
	public void avanzarTiempo(){
		this.timer += AVANCE_TIEMPO;
		this.timerBotellas += AVANCE_TIEMPO;
		//System.out.println("tiempo actual: " + this.timer);
		
	}
	
	public void ejecutarAnimacion(){
		if(cinta.buscarPosicion(this.etiquetador.getPosicion().getX())){
			this.cinta.detenerCinta();
			this.etiquetador.animar();
			//System.out.println("animacion de etiquetador");
			this.cinta.activarCinta();
		}
		if (cinta.buscarPosicion(this.rellenador.getPosicion().getX())){
			this.cinta.detenerCinta();
			this.rellenador.animar();
			//System.out.println("animacion de rellenador");
			this.cinta.activarCinta();
		}
	}
	
	public void producir(){  // TODO este metodo mueve toda la produccion
		if (! cinta.estaLlenaDeBotellas()){
			if(cinta.estaAvanzando()){
				cinta.avanzarCinta();
				if(timerBotellas >= TIEMPO_ENTRE_BOTELLAS){
					cinta.recibirBotella(this.expededoraBotellas.entregarBotella());
					timerBotellas = 0.0f;
					//System.out.println("dispenser entrego botella a cinta");
				}	
				
			}
		}	
			else{
				if(cinta.estaAvanzando()){
					
					if(cinta.buscarPosicion(this.empaquetador.getPosicion().getX())){
						this.cinta.detenerCinta();
						this.empaquetador.recibirBotella(this.cinta.entregarBotella());
						//System.out.println("cinta entrego botella a empaquetador");
						this.cinta.activarCinta();
						this.cinta.avanzarCinta();
						//this.cinta.recibirBotella(this.expededoraBotellas.entregarBotella());
						//System.out.println("dispenser entrego botella a cinta");
					}
					else 
						cinta.avanzarCinta();
					/*
					this.empaquetador.recibirBotella(this.cinta.entregarBotella());
					System.out.println("cinta entrego botella a empaquetador");
					this.cinta.avanzarCinta();
					this.cinta.recibirBotella(this.expededoraBotellas.entregarBotella());
					System.out.println("dispenser entrego botella a cinta");
				*/
				}	
			}		
		ejecutarAnimacion();
		//System.out.println("num de bot en cinta: " + this.cinta.getNumeroDeBotellas());
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
	

	


