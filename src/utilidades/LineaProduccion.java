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

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

import shader.ManejoShaders2;
import shader.ManejoShadersMejorado;

public class LineaProduccion implements Observer {
	
	private Dispenser expededoraBotellas;
	private CintaTransportadora cinta;
	private Etiquetador etiquetador;
	private Rellenador rellenador;
	private Empaquetador empaquetador;
	private Rampa rampa;
	private float timer = 0.0f;	// timer total desde que se inicia la produccion.
	private float timerBotellas = 0.0f;	// hace cuanto salio la ultima botella del dispenser 
	private static float AVANCE_TIEMPO = 0.05f;
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
		this.etiquetador = new Etiquetador(this, TIEMPO_ETIQUETADO);
		this.expededoraBotellas = new Dispenser(this, shader, glut, glu, gLDrawable);
		this.rellenador = new Rellenador(this);
	}
	
	public void avanzarTiempo(){
		this.timer += AVANCE_TIEMPO;
		this.timerBotellas += AVANCE_TIEMPO;
		//System.out.println("tiempo actual: " + this.timer);
		
	}
	
	public void ejecutarAnimacion(){	// falta comparar con area de incidencia
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
						
						this.cinta.activarCinta();
						this.cinta.avanzarCinta();						
					}
					else 
						cinta.avanzarCinta();					
				}	
			}		
		ejecutarAnimacion();
		//System.out.println("num de bot en cinta: " + this.cinta.getNumeroDeBotellas());
	}
	
	public CintaTransportadora getCinta() {
		return cinta;
	}
	
	public LineaProduccion dibujar(GLAutoDrawable gLDrawable){
		
		final GL2 gl = gLDrawable.getGL().getGL2();
		
		gl.glPushMatrix();
			//gl.glScalef(0.2f, 0.2f, 0.2f);
		
			gl.glPushMatrix();
				gl.glTranslatef(0,-6.5f,0);
				glut.glutSolidCube(10);
			gl.glPopMatrix();
			
			//El Y del Vertice da la altura o sea la distancia al piso, NO CAMBIAR SI SE VEN EN EL PISO
//			this.expededoraBotellas.setPosicion(new Vertice(3f, -0.78f, 0.6f)).rotar(180f).dibujar(gLDrawable);
//			this.empaquetador.setPosicion(new Vertice(-4f, -0.78f, 0.35f)).dibujar(gLDrawable);
//			this.rellenador.setPosicion(new Vertice(0.3f, -0.78f, -0.7f)).rotar(-90f).dibujar(gLDrawable);
//			this.etiquetador.setPosicion(new Vertice(-1.9f, -0.78f, 0.5f)).rotar(90f).dibujar(gLDrawable);
			
	//		this.rampa.dibujar(gLDrawable);
			
			
			this.cinta.setPosicion(new Vertice(-4f, -0.78f, 0)).dibujar(gLDrawable);
		gl.glPopMatrix();
		
		return this;
	}
	
	public LineaProduccion actualizar(){
		this.avanzarTiempo();
		this.producir();
		
		return this;
	}
	@Override
	public void update(Observable o, Object arg) {
		
	}
		
}
	

	


