package objetosEscena;

import javax.media.opengl.GL2;

import utilidades.Animable;
import utilidades.Objeto3D;
import utilidades.Objeto3DConAnimacion;
import utilidades.Vertice;


public class Rellenador extends Objeto3DConAnimacion implements Animable {
	
	private boolean estaLlenando;
	private boolean terminoLlenar;
	private int pasosDeLlenado;
	private int pasoActual;

	public void liberarLiquido(){}
	
	public Rellenador(float avanceLlenado) {
		super("model/examples/models/obj/llenadora.obj", "model/examples/models/obj/llenadora-brazo.obj");
		posicionArticulacion = posicionArticulacionInicial = 1f;
		posicionArticulacionFinal = 0.6f;
		avanceArticulacion = avanceLlenado;
		estaLlenando = false;
		terminoLlenar = true;
		pasosDeLlenado = 40;
		pasoActual = 0;
		
		this.posicion = new Vertice(0f,0f,0f);
	}
		

	@Override
	public void animar() {
		if(estaLlenando) {
			if(estaBajando)
			{
				if(posicionArticulacion <= posicionArticulacionFinal) {
					estaBajando = false;
					terminoLlenar = false;
				}
				else
					posicionArticulacion -= avanceArticulacion;
			}
			else if(!terminoLlenar) {
				//Realizo el llenado
				if(pasosDeLlenado == pasoActual) {
					terminoLlenar = true;
					pasoActual = 0;
				}
				else
					pasoActual++;
			}
			else {
				if(posicionArticulacion >= posicionArticulacionInicial) {
					estaLlenando = false;
					estaBajando = true;
					terminoLlenar = true;
					posicionArticulacion = posicionArticulacionInicial;
				}
				else
					posicionArticulacion += avanceArticulacion;
			}	
		}
	}
	
	public Rellenador rellenar(Botella botella){
		estaLlenando = true;	
		estaBajando = true;
		return this;
	}
	
	public boolean estaLlenando() {
		return estaLlenando;
	}
	
	public Vertice zonaDeRellenado() {
		return new Vertice(4.324147f, 0.15712146f, 0.0f);
	}

	@Override
	protected void transformacionesArticulacion(GL2 gl) {
		gl.glRotatef(90, 0, 1f, 0);
		gl.glTranslatef(0f, posicionArticulacion, 0.49f);
		//Altura mas abajo y=0.8f
		//Altura mas arriba y=2.2f		
	}
}
