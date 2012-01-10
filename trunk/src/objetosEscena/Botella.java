package objetosEscena;
import java.util.Observable;

import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;


public class Botella  implements Dibujable,Animable {
	private boolean lleno;
	private boolean etiquetado;
	private float porcentajeLlenado;
	private static float altura = 3.0f;
	private Vertice posicion;
	
	
	public Botella(){
		this.lleno = false;
		this.etiquetado = false;
		this.posicion = new Vertice(0f,0f,0f);
	}

	public void setPorcentajelLlenado(float porcentaje){
		this.porcentajeLlenado = porcentaje;
	}
	
	public void setPosicion(Vertice vert){
		this.posicion.setX(vert.getX());
		this.posicion.setY(vert.getY());
		this.posicion.setZ(vert.getZ());
	}
	
	public Vertice getPosicion(){
		return this.posicion;
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
		System.out.println("Se dibujo botella");
		
	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}
}
