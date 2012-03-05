package objetosEscena;

import utilidades.Animable;
import utilidades.Dibujable;
import utilidades.LineaProduccion;
import utilidades.Vertice;

import java.util.Observable;
import java.util.Observer;

import javax.media.opengl.GLAutoDrawable;

public class Rampa extends Observable implements Dibujable,Observer,Animable{
	
	
	public void setPendiente(float angulo){}
	public void setLargo(float largo){}
	public void setAncho(float ancho){}
	private Vertice posicion;
	
	public Rampa(LineaProduccion linea){
		addObserver(linea);
	}
	
	public void recibirPackBotellas(){}

	public Vertice getPosicion(){
		return this.posicion;
	}
	
	@Override
	public void dibujar(GLAutoDrawable gLDrawable) {
		//System.out.println("Se dibujo rampa");
		
	}
	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		this.animar();
	}
	
	@Override
	public void animar() {
		// TODO Auto-generated method stub
		System.out.println("se anima rampa");
	}

}
