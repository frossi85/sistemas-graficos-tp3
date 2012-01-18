package utilidades;

import java.util.ArrayList;

public class SuperficieDeRevolucion implements Dibujable {
	
	private BezierCubica curva1;
	private BezierCubica curva2;
	private BezierCubica curva3;
	private BezierCubica curva4;
	
	private float z;
	
	public SuperficieDeRevolucion(ArrayList<PuntoDeControl>list,float z){	// entre las 4 curvas se forma un circulo.La sup posee una coord z
		this.curva1 = new BezierCubica(list);
		this.curva2 = new BezierCubica(list);
		curva2.setPuntoDeControl(0, list.get(1));
		curva2.setPuntoDeControl(1, list.get(2));
		curva2.setPuntoDeControl(2, list.get(3));
		curva2.setPuntoDeControl(3, list.get(0));
		this.curva3 = new BezierCubica(list);
		curva3.setPuntoDeControl(0, list.get(2));
		curva3.setPuntoDeControl(1, list.get(3));
		curva3.setPuntoDeControl(2, list.get(0));
		curva3.setPuntoDeControl(3, list.get(1));
		this.curva4 = new BezierCubica(list);
		curva4.setPuntoDeControl(0, list.get(3));
		curva4.setPuntoDeControl(1, list.get(0));
		curva4.setPuntoDeControl(2, list.get(1));
		curva4.setPuntoDeControl(3, list.get(2));
		
		this.z = z;
	}

	@Override
	public void dibujar() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
