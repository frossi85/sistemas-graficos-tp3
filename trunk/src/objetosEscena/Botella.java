package objetosEscena;
import java.util.Observable;

import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;


public class Botella extends Observable implements Dibujable {
	private boolean lleno;
	private boolean etiquetado;
	private float porcentajeLlenado;
	private static float altura = 3.0f;
	private Vertice posicion;
	
	
	public Botella(LineaProduccion linea){
		this.lleno = false;
		this.etiquetado = false;
		addObserver(linea);
	}

	public void setPorcentajelLlenado(float porcentaje){
		this.porcentajeLlenado = porcentaje;
	}
	
	public void setPosicion(float x, float y, float z){
		this.posicion.setX(x);
		this.posicion.setY(y);
		this.posicion.setZ(z);
	}
	
	public boolean estaLleno() {
		return lleno;
	}

	public void setLleno(boolean lleno) {
		this.lleno = lleno;
	}

	public boolean estaEtiquetado() {
		return etiquetado;
	}

	public void setEtiquetado(boolean etiquetado) {
		this.etiquetado = etiquetado;
	}

	public float getPorcentajeLlenado() {
		return porcentajeLlenado;
	}

	public void setPorcentajeLlenado(float porcentajeLlenado) {
		this.porcentajeLlenado = porcentajeLlenado;
	}

	public float getAltura() {
		return altura;
	}

	@Override
	public void dibujar() {
		// TODO Auto-generated method stub
		
	}
}
