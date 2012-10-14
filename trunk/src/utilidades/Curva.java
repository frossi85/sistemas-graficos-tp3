package utilidades;
import java.util.ArrayList;

public abstract class Curva {

	protected ArrayList<PuntoDeControl> puntosDeControl;
	
	public Curva(ArrayList<PuntoDeControl>list){
		this.puntosDeControl = list;
	}
	
	public int cantPuntosControl(){
		return this.puntosDeControl.size();
	}
	
	public void setPuntoDeControl(int index,PuntoDeControl punto){
		this.puntosDeControl.add(index, punto);
	}	
}
