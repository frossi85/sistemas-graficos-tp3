package objetosEscena;
import java.util.Observable;
import aplicacion.Renderer;

public class Botella extends Observable{
	private boolean lleno;
	private boolean etiquetado;
	private float porcentajeLlenado;
	private static float altura = 3.0f;
	
	
	public Botella(Renderer renderer){
		this.lleno = false;
		this.etiquetado = false;
		addObserver(renderer);
	}

	public void setPorcentajelLlenado(float porcentaje){
		this.porcentajeLlenado = porcentaje;
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

	public void setAltura(float altura) {
		this.altura = altura;
	}
}
