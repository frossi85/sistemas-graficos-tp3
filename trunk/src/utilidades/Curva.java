package utilidades;
import java.util.ArrayList;

public abstract class Curva {

	protected ArrayList<Vertice> puntosDeControl;
	
	public Curva(ArrayList<Vertice>list){
		this.puntosDeControl = list;
	}
	
	public int cantPuntosControl(){
		return this.puntosDeControl.size();
	}
	
	public void setPuntoDeControl(int index,Vertice punto){
		this.puntosDeControl.add(index, punto);
	}	
}
