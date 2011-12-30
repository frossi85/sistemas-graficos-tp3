package objetosEscena;

import java.util.List;
import java.util.Queue;

import javax.management.Query;

import utilidades.Dibujable;

public class CintaTransportadora implements Dibujable {

	private int capacidadBotellas;
	Queue<Botella> botellas;
	
	public CintaTransportadora(int capacidadBotellas){
		this.capacidadBotellas = capacidadBotellas;
	} //cant max de botellas que puede tener la cinta transportadora
	
	public void recibirBotella(Botella botella){ 
		botellas.add(botella);
		
	}
	
	public Botella entragarBotella(){
		return botellas.poll();
	}
	
	
	public void setVelocidad(float vel){}
	public void detenerCinta(){} // detiene cinta
	public void activarCinta(){}
	public boolean estaAvanzando(){return true;}
	public float getVelocidad(){return 0.0f;}
	
	public boolean estaLlenaDeBotellas(){
		if(this.botellas.size() == this.capacidadBotellas)
			return true;
		else
			return false;
		
	}
	
	@Override
	public void dibujar() {
		// TODO Auto-generated method stub

	}

}
