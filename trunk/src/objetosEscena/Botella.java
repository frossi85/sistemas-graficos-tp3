package objetosEscena;
import java.util.Observable;
import aplicacion.Renderer;

public class Botella extends Observable{
	private boolean lleno;
	private boolean etiquetado;
	
	public Botella(boolean lleno, boolean etiquetado,Renderer renderer){
		this.lleno = lleno;
		this.etiquetado = etiquetado;
		addObserver(renderer);
		
	}
	
	//TODO: Me falta a
}
