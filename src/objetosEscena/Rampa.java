package objetosEscena;

import utilidades.Dibujable;
import utilidades.LineaProduccion;

import java.util.Observable;
import java.util.Observer;

public class Rampa extends Observable implements Dibujable,Observer{
	
	
	public void setPendiente(float angulo){}
	public void setLargo(float largo){}
	public void setAncho(float ancho){}
	
	public Rampa(LineaProduccion linea){
		addObserver(linea);
	}
	
	public void recibirPackBotellas(){}

	@Override
	public void dibujar() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (o instanceof Empaquetador){
			Empaquetador em = (Empaquetador)o;
			if(em.getCantidadBotellasRecibidas() == em.CAPACIDAD_BOTELLAS){	// si el empaquetador tiene 4 botellas notifico que debo cambiar estado
				setChanged();
		        notifyObservers();
		        clearChanged();
			}
		}
	}

}
