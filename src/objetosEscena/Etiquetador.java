package objetosEscena;

import javax.media.opengl.GL2;
import utilidades.Animable;
import utilidades.Objeto3DConAnimacion;
import utilidades.Vertice;

public class Etiquetador extends Objeto3DConAnimacion implements Animable {
	
	boolean estaEtiquetando;
		
	public Etiquetador(float avanceEtiquetado){
		super("model/examples/models/obj/etiquetadora.obj", "model/examples/models/obj/etiquetadora-brazo.obj");
		posicionArticulacion = posicionArticulacionInicial = 2.2f;
		posicionArticulacionFinal = 0.8f;
		avanceArticulacion = avanceEtiquetado;
		estaEtiquetando = false;
		
		this.posicion = new Vertice(0f,0f,0f);
	}
	
	public Etiquetador etiquetar(Botella botella){
		estaEtiquetando = true;	
		estaBajando = true;
		return this;
	}
	
	public boolean estaEtiquetando() {
		return estaEtiquetando;
	}
	
	@Override
	public void animar() {
		if(estaEtiquetando) {
			if(estaBajando)
			{
				if(posicionArticulacion <= posicionArticulacionFinal)
					estaBajando = false;
				else
					posicionArticulacion -= avanceArticulacion;
			}
			else {
				if(posicionArticulacion >= posicionArticulacionInicial) {
					estaEtiquetando = false;
					estaBajando = true;
					posicionArticulacion = posicionArticulacionInicial;
				}
				else
					posicionArticulacion += avanceArticulacion;
			}	
		}
	}
	
	public Vertice zonaDeEtiquetado() {
		return new Vertice(2.2032585f, 0.14055142f, 0.0f);
	}
	
	@Override
	protected void transformacionesArticulacion(GL2 gl) {
		gl.glScalef(0.4f, 0.4f, 0.4f);	
		gl.glTranslatef(0.65f, posicionArticulacion, 0);
		gl.glRotatef(180, 0, 0, 1f);
		
		//Altura mas abajo y=0.8f
		//Altura mas arriba y=2.2f
	}
}
