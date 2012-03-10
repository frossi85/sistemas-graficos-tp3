package utilidades;
import java.util.ArrayList;
import java.lang.Math;

public class BezierCubica extends Curva {
	
	private final float codError = -9999f; 
	
	public BezierCubica(ArrayList<PuntoDeControl>list){
		super(list);
	}
	
	private float getB0(float u){
		return (float) (Math.pow(1f-u,3f));
	}
	
	private float getB1(float u){
		return (float) (3f*u*Math.pow(1f-u,2f));
	}
	
	private float getB2(float u){
		return (float) (3f*Math.pow(u, 2f)*(1f-u));
	}
	
	private float getB3(float u){
		return (float) (Math.pow(u, 3f));
	}
	
	
	public float getX(float u){
		if(this.puntosDeControl.size() != 4){
			return this.codError;
		}
		else{
			return (float) (this.puntosDeControl.get(0).getX()*getB0(u)+puntosDeControl.get(1).getX()*getB1(u)+puntosDeControl.get(2).getX()*getB2(u)+puntosDeControl.get(3).getX()*getB3(u));
		}
	}
	
	public float getY(float u){
		if(this.puntosDeControl.size() != 4){
			return this.codError;
		}
		else{
			return (float) (this.puntosDeControl.get(0).getY()*getB0(u)+puntosDeControl.get(1).getY()*getB1(u)+puntosDeControl.get(2).getY()*getB2(u)+puntosDeControl.get(3).getY()*getB3(u));
		}
	}
}
