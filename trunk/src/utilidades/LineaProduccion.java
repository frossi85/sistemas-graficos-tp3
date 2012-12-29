package utilidades;

import objetosEscena.CintaTransportadora;
import objetosEscena.Dispenser;
import objetosEscena.Empaquetador;
import objetosEscena.Etiquetador;
import objetosEscena.Rampa;
import objetosEscena.Rellenador;

import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.glu.GLU;

import com.jogamp.opengl.util.gl2.GLUT;

import shader.ManejoShadersMejorado;

public class LineaProduccion {
	
	private Dispenser expededoraBotellas;
	private CintaTransportadora cinta;
	private Etiquetador etiquetador;
	private Rellenador rellenador;
	private Empaquetador empaquetador;
	private Rampa rampa;
	private float timer = 0.0f;	// timer total desde que se inicia la produccion.
	private float timerBotellas = 0.0f;	// hace cuanto salio la ultima botella del dispenser 
	private static float AVANCE_TIEMPO = 0.01f;//0.02f;
	private static float 	TIEMPO_ENTRE_BOTELLAS = 1f;	// tiempo de demora entre que sale una botella del dispenser a otra
	private static float VELOCIDAD_CINTA = 1f;
	private static int CAPACIDAD_CINTA = 10;
	private static float TIEMPO_ETIQUETADO = 3f;
	private static float AVANCE_BOTELLAS = VELOCIDAD_CINTA*AVANCE_TIEMPO;  //distancia = tiempo*velocidad
    private GLUT glut = new GLUT();
	
	public LineaProduccion(ManejoShadersMejorado shader, GLUT glut, GLU glu, GLAutoDrawable gLDrawable){
		//Creacion de los elementos de la linea de produccion
		rampa = new Rampa(this);
		empaquetador = new Empaquetador(this, rampa);
		etiquetador = new Etiquetador(TIEMPO_ETIQUETADO);
		expededoraBotellas = new Dispenser(this, shader, glu);
		rellenador = new Rellenador(this);
		cinta = new CintaTransportadora(CAPACIDAD_CINTA, AVANCE_BOTELLAS, etiquetador, rellenador);
		
		//Posicionamiento de los elementos de la linea de produccion
		//El Y del Vertice da la altura o sea la distancia al piso, NO CAMBIAR SI SE VEN EN EL PISO
		empaquetador.setPosicion(new Vertice(3f, -0.78f, 0.6f)).rotar(180f);
		expededoraBotellas.setPosicion(new Vertice(-4f, -0.78f, 0.35f));	
		etiquetador.setPosicion(new Vertice(-1.8f, -0.60f, 0.47f)).rotar(90f);
		cinta.setPosicion(new Vertice(-4f, -0.78f, 0));
		rellenador.setPosicion(new Vertice(0.3f, -0.78f, -0.7f)).rotar(-90f);	
//		rampa.setPosicion(new Vertice(-1.8f, -0.60f, 0.47f));
	}
	
	public void avanzarTiempo(){
		this.timer += AVANCE_TIEMPO;
		this.timerBotellas += AVANCE_TIEMPO;
		//System.out.println("tiempo actual: " + this.timer);
		
	}
	
	public void animar(){	// falta comparar con area de incidencia
		rellenador.animar();
		//empaquetador.animar();

		etiquetador.animar();
		cinta.animar();
//		expededoraBotellas.animar();
		rampa.animar();
	}
	
	public void producir(){  // TODO este metodo mueve toda la produccion
		if (! cinta.estaLlenaDeBotellas()){
			if(cinta.estaAvanzando()){
				//cinta.avanzarCinta();
				if(timerBotellas >= TIEMPO_ENTRE_BOTELLAS){
					cinta.recibirBotella(this.expededoraBotellas.entregarBotella());
					timerBotellas = 0.0f;
					//System.out.println("dispenser entrego botella a cinta");
				}	
				
			}
		}	
		else{
			if(cinta.estaAvanzando()){
				
//					if(cinta.buscarPosicion(this.empaquetador.getPosicion().getX())){
//						this.cinta.detenerCinta();
//						this.empaquetador.recibirBotella(this.cinta.entregarBotella());
//						
//						this.cinta.activarCinta();					
//					}			
			}	
		}	

		animar();
		
		if(!cinta.estaDetenida())
			cinta.avanzarCinta();
		

	}
	
	public CintaTransportadora getCinta() {
		return cinta;
	}
	
	public LineaProduccion dibujar(){
		
		final GL2 gl = GLProvider.getGL2();
		
		gl.glPushMatrix();	
			gl.glPushMatrix();
				gl.glTranslatef(0,-6.5f,0);
				glut.glutSolidCube(10);
			gl.glPopMatrix();
			
			empaquetador.dibujar();
			expededoraBotellas.dibujar();
			rellenador.dibujar();
			etiquetador.dibujar();	
			cinta.dibujar();
			rampa.dibujar();
		gl.glPopMatrix();
		
		return this;
	}
	
	public LineaProduccion actualizar(){
		this.avanzarTiempo();
		this.producir();
		
		return this;
	}
}
	

	


