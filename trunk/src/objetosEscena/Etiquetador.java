package objetosEscena;

import utilidades.Dibujable;

public class Etiquetador implements Dibujable {
	
	private float tiempoEtiquetado;
	private boolean terminoEtiquetado;
	
	public Etiquetador(float tiempoEtiquetado){
		this.tiempoEtiquetado = tiempoEtiquetado;
	}
	
	public void etiquetar(){}
	
	//TODO: No se si es necesario
	public boolean terminoDeEtiquetar(){return terminoEtiquetado;}

	@Override
	public void dibujar() {
		// TODO Auto-generated method stub
		
	}

}
