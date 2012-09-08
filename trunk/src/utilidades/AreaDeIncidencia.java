package utilidades;


public class AreaDeIncidencia {
	
	private Vertice vertice1;
	private Vertice vertice2;
	private Vertice vertice3;
	private Vertice vertice4;
	private float [] maximos;	// x,y,z maximos respectivamente
	private float [] minimos; 	// x,y,z minimos respectivamente
	
	public AreaDeIncidencia(Vertice vert1, Vertice vert2, Vertice vert3, Vertice vert4){	// consideramos el caso en q el area es un plano.
		vertice1 = vert1;
		vertice2 = vert2;
		vertice3 = vert3;
		vertice4 = vert4;
		maximos = new float[3];
		minimos = new float[3];
		
		
		
	}
	
	public boolean estaAdentro(){
		return true;
	}
	
	

	

}
