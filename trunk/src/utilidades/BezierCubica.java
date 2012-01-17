package utilidades;
import java.util.ArrayList;
import java.lang.Math;

public class BezierCubica extends Curva {
	
	private final float codError = -9999f; 
	
	public BezierCubica(ArrayList<PuntoDeControl>list){
		super(list);
	}
	
	private float getB0(float u){
		return (float) (Math.pow(u, 0f)*Math.pow(1f-u,3f));
	}
	
	private float getB1(float u){
		return (float) (6f/(2f)*Math.pow(u, 1f)*Math.pow(1f-u,2f));
	}
	
	private float getB2(float u){
		return (float) (6f/(2f)*Math.pow(u, 2f)*Math.pow(1f-u,1f));
	}
	
	private float getB3(float u){
		return (float) (Math.pow(u, 3f)*Math.pow(1f-u,0f));
	}
	
	
	public float getX(float u){
		if(this.puntosDeControl.size() != 4){
			return this.codError;
		}
		else{
			return (float) (this.puntosDeControl.get(0).getX()*Math.pow(1f-u,3f)/Math.pow(this.getB0(u),3f) +
		this.puntosDeControl.get(1).getX()*3f*u*Math.pow(1-u,2f)/Math.pow(this.getB1(u),3f)+
		this.puntosDeControl.get(2).getX()*3f*Math.pow(u, 2)*(1f-u)/Math.pow(this.getB2(u), 3)+
		this.puntosDeControl.get(3).getX()*Math.pow(u, 3f)/Math.pow(this.getB3(u), 3f));
		}
	}
	
	public float getY(float u){
		if(this.puntosDeControl.size() != 4){
			return this.codError;
		}
		else{
			return (float) (this.puntosDeControl.get(0).getY()*Math.pow(1f-u,3f)/Math.pow(this.getB0(u),3f) +
		this.puntosDeControl.get(1).getY()*3f*u*Math.pow(1-u,2f)/Math.pow(this.getB1(u),3f)+
		this.puntosDeControl.get(2).getY()*3f*Math.pow(u, 2)*(1f-u)/Math.pow(this.getB2(u), 3)+
		this.puntosDeControl.get(3).getY()*Math.pow(u, 3f)/Math.pow(this.getB3(u), 3f));
		}
	}
}
