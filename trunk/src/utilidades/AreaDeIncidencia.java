package utilidades;


public class AreaDeIncidencia {
	
	private Vertice vertice1;
	private Vertice vertice2;
	private Vertice vertice3;
	private float [] maximos;	// x,y,z maximos respectivamente
	private float [] minimos; 	// x,y,z minimos respectivamente
	
	public AreaDeIncidencia(Vertice vert1, Vertice vert2, Vertice vert3){	// consideramos el caso en q el area es un plano. (se genera con 3 puntos)
		
		vertice1 = vert1;
		vertice2 = vert2;
		vertice3 = vert3;
		
		maximos = new float[3];
		minimos = new float[3];
		
		maximos[0] = getMaximo(vert1.getX(), vert2.getX(), vert3.getX());
		maximos[1] = getMaximo(vert1.getY(), vert2.getY(), vert3.getY());
		maximos[2] = getMaximo(vert1.getZ(), vert2.getZ(), vert3.getZ());
		
		minimos[0] = getMinimo(vert1.getX(), vert2.getX(), vert3.getX());
		minimos[1] = getMinimo(vert1.getY(), vert2.getY(), vert3.getY());
		minimos[2] = getMinimo(vert1.getZ(), vert2.getZ(), vert3.getZ());
				
	}
	
	public boolean estaAdentro(Vertice p1, Vertice p2, Vertice p3){
		float maxTemp[] = new float [3];	// representan los max y min del area pasada por parametro
		float minTemp[] = new float [3];
		
		maxTemp = getMaximos(p1, p2, p3);
		minTemp = getMinimos(p1, p2, p3);
		
		return(estaAdentroX(maxTemp[0], minTemp[0]) && (estaAdentroY(maxTemp[1], minTemp[1])) && (estaAdentroZ(maxTemp[2], minTemp[2])));
	}
	
	float[] getMaximos(Vertice p1, Vertice p2, Vertice p3){
		float max[] = new float[3];
		max[0] = getMaximo(p1.getX(), p2.getX(), p3.getX());
		max[1] = getMaximo(p1.getY(), p2.getY(), p3.getY());
		max[2] = getMaximo(p1.getZ(), p2.getZ(), p3.getZ());
		return max;		
	}
	
	float getMaximo(float a, float b, float c){
		if(a >= b){
			if(a >= c)return a; return c;
		}
		else{
			if(b >= c) return b; return c;
		}
	}
	
	float[] getMinimos(Vertice p1, Vertice p2, Vertice p3){
		float min[] = new float[3];
		min[0] = getMinimo(p1.getX(), p2.getX(), p3.getX());
		min[1] = getMinimo(p1.getY(), p2.getY(), p3.getY());
		min[2] = getMinimo(p1.getZ(), p2.getZ(), p3.getZ());
		return min;		
	}
	
	float getMinimo(float a, float b, float c){
		if(a <= b){
			if(a <= c)return a; return c;
		}
		else{
			if(b <= c) return b; return c;
		}
	}
	
	boolean estaAdentroX(float xMax, float xMin){
		return ((xMax <= maximos[0]) && (xMin >= minimos[0]));
	}
	
	boolean estaAdentroY(float yMax, float yMin){
		return ((yMax <= maximos[1]) && (yMin >= minimos[1]));
	}
	
	boolean estaAdentroZ(float zMax, float zMin){
		return ((zMax <= maximos[2]) && (zMin >= minimos[2]));
	}

}
