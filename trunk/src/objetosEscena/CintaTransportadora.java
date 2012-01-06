package objetosEscena;

import java.util.Observable;
import java.util.Queue;

import javax.swing.text.html.HTMLDocument.Iterator;

import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

public class CintaTransportadora extends Observable implements Dibujable,Animable {

	private int capacidadBotellas;
	Queue<Botella> botellas;
	private float velocidadCinta;
	private float avanceBotellas;
	
	public CintaTransportadora(int capacidadBotellas, LineaProduccion linea,float velCinta, float avanceBotellas){
		this.capacidadBotellas = capacidadBotellas;  //cant max de botellas que puede tener la cinta transportadora
		this.velocidadCinta = velCinta;
		this.avanceBotellas = avanceBotellas;
		addObserver(linea);
	} 
	
	public void recibirBotella(Botella botella){ 
		botellas.add(botella);
		if (this.estaLlenaDeBotellas()){	// cuando se llena de botellas notifica al observador
			setChanged();
	        notifyObservers();
	        clearChanged();
		}
		
	}
	
	public Botella entragarBotella(){
		return botellas.poll();
	}
	
	public void avanzarCinta(){
		for(int i = 0; i < this.botellas.size(); i++){
			java.util.Iterator<Botella> it =  this.botellas.iterator();
			while(it.hasNext()){
				Botella bot = it.next();
				avanzarBotella(bot);
			}
		}
	}
	
	public void avanzarBotella(Botella botella){	// por ahora solo avanza en coord x
		Vertice ver = botella.getPosicion();
		ver.setX(ver.getX() + this.avanceBotellas);
		botella.setPosicion(ver);
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
	
	public boolean buscarPosicion(float posicion ){	// busca si existe una botella en una posicion dada de la cinta
		for(int i = 0; i < this.botellas.size(); i++){
			java.util.Iterator<Botella> it =  this.botellas.iterator();
			while(it.hasNext()){
				Botella bot = it.next();
				if(bot.getPosicion().getX() == posicion) return true;
			}
		}
		return false;
	}
	
	@Override
	public void dibujar() {
		// TODO Auto-generated method stub

	}

	@Override
	public void animar() {
		// TODO Auto-generated method stub
		
	}

}
